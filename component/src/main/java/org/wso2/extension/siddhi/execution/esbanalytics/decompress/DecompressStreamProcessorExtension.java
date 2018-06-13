package org.wso2.extension.siddhi.execution.esbanalytics.decompress;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.wso2.carbon.analytics.spark.core.util.AnalyticsConstants;
import org.wso2.carbon.analytics.spark.core.util.CompressedEventAnalyticsUtils;
import org.wso2.carbon.analytics.spark.core.util.PublishingPayload;
import org.wso2.extension.siddhi.execution.esbanalytics.decompress.util.CompressedEventUtils;
import org.wso2.siddhi.annotation.Example;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.annotation.Parameter;
import org.wso2.siddhi.annotation.ReturnAttribute;
import org.wso2.siddhi.annotation.util.DataType;
import org.wso2.siddhi.core.config.SiddhiAppContext;
import org.wso2.siddhi.core.event.ComplexEventChunk;
import org.wso2.siddhi.core.event.stream.StreamEvent;
import org.wso2.siddhi.core.event.stream.StreamEventCloner;
import org.wso2.siddhi.core.event.stream.populater.ComplexEventPopulater;
import org.wso2.siddhi.core.exception.SiddhiAppCreationException;
import org.wso2.siddhi.core.exception.SiddhiAppRuntimeException;
import org.wso2.siddhi.core.executor.ExpressionExecutor;
import org.wso2.siddhi.core.executor.VariableExpressionExecutor;
import org.wso2.siddhi.core.query.processor.Processor;
import org.wso2.siddhi.core.query.processor.stream.StreamProcessor;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.query.api.definition.AbstractDefinition;
import org.wso2.siddhi.query.api.definition.Attribute;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

import static org.wso2.extension.siddhi.execution.esbanalytics.decompress.util.ESBAnalyticsConstants.TYPE_BOOL;
import static org.wso2.extension.siddhi.execution.esbanalytics.decompress.util.ESBAnalyticsConstants.TYPE_DOUBLE;
import static org.wso2.extension.siddhi.execution.esbanalytics.decompress.util.ESBAnalyticsConstants.TYPE_FLOAT;
import static org.wso2.extension.siddhi.execution.esbanalytics.decompress.util.ESBAnalyticsConstants.TYPE_INTEGER;
import static org.wso2.extension.siddhi.execution.esbanalytics.decompress.util.ESBAnalyticsConstants.TYPE_LONG;
import static org.wso2.extension.siddhi.execution.esbanalytics.decompress.util.ESBAnalyticsConstants.TYPE_STRING;

/**
 * Decompress streaming analytics events coming from the WSO2 Enterprise Integrator
 */
@Extension(
        name = "decompress",
        namespace = "esbAnalytics",
        description = "This extension decompress any compressed analytics events coming from WSO2 Enterprice" +
                " Integrator",
        parameters = {
                @Parameter(name = "meta.compressed",
                        description = "Compressed state of the message",
                        type = {DataType.BOOL}),
                @Parameter(name = "meta.tenant.id",
                        description = "Tenant id",
                        type = {DataType.INT}),
                @Parameter(name = "message.id",
                        description = "Message id",
                        type = {DataType.STRING}),
                @Parameter(name = "flow.data",
                        description = "Compressed stream events chunk",
                        type = {DataType.STRING})
        },
        returnAttributes = {
                @ReturnAttribute(name = "messageFlowId",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "host",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "hashCode",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "componentName",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "componentType",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "componentIndex",
                        description = "-",
                        type = {DataType.INT}),
                @ReturnAttribute(name = "componentId",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "startTime",
                        description = "-",
                        type = {DataType.LONG}),
                @ReturnAttribute(name = "endTime",
                        description = "-",
                        type = {DataType.LONG}),
                @ReturnAttribute(name = "duration",
                        description = "-",
                        type = {DataType.LONG}),
                @ReturnAttribute(name = "beforePayload",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "afterPayLoad",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "contextPropertyMap",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "transportPropertyMap",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "children",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "entryPoint",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "entryPointHashcode",
                        description = "-",
                        type = {DataType.STRING}),
                @ReturnAttribute(name = "faultCount",
                        description = "-",
                        type = {DataType.INT}),
                @ReturnAttribute(name = "metaTenantId",
                        description = "-",
                        type = {DataType.INT}),
                @ReturnAttribute(name = "timestamp",
                        description = "-",
                        type = {DataType.LONG})
        },
        examples = {
                @Example(
                        syntax = "define stream inputStream(meta_compressed bool, meta_tenantId int," +
                                " messageId string, flowData string); " + "@info( name = 'query') from " +
                                "inputStream#esbAnalytics:decompress(meta_compressed, meta_tenantId, " +
                                "messageId, flowData) insert all events into outputStream;",
                        description = "This query uses the incoming esb analytics message to produce decompressed " +
                                "esb analytics events."
                )
        }
)
public class DecompressStreamProcessorExtension extends StreamProcessor {

    private static final ThreadLocal<Kryo> kryoTL = ThreadLocal.withInitial(() -> {

        Kryo kryo = new Kryo();
        /* Class registering precedence matters. Hence intentionally giving a registration ID */
        kryo.register(HashMap.class, 111);
        kryo.register(ArrayList.class, 222);
        kryo.register(PublishingPayload.class, 333);
        return kryo;
    });

    private Map<String, String> fields = new LinkedHashMap<>();
    private int[] dataColumnIndex;
    private int[] metaCompressedIndex;
    private int[] metaTenantIdIndex;

    /**
     * Get the definition of the output fields
     *
     * @return Name and type of decompressed fields
     */
    private static Map<String, String> getOutputFields() {

        Map<String, String> fields = new LinkedHashMap<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            String[] lines = IOUtils.toString(classLoader.getResourceAsStream("decompressedEventDefinition"))
                    .split("\n");
            for (String line : lines) {
                if (!StringUtils.startsWithIgnoreCase(line, "#") && StringUtils.isNotEmpty(line)) {
                    String[] fieldDef = StringUtils.deleteWhitespace(line).split(":");
                    if (fieldDef.length == 2) {
                        fields.put(fieldDef[0], fieldDef[1]);
                    }
                }
            }
        } catch (IOException e) {
            throw new SiddhiAppCreationException("Error occurred while reading decompressed event definitions: "
                    + e.getMessage(), e);
        }
        return fields;
    }

    @Override
    protected void process(ComplexEventChunk<StreamEvent> streamEventChunk, Processor nextProcessor,
                           StreamEventCloner streamEventCloner, ComplexEventPopulater complexEventPopulater) {

        ComplexEventChunk<StreamEvent> decompressedStreamEventChunk = new ComplexEventChunk<>(false);
        while (streamEventChunk.hasNext()) {
            StreamEvent compressedEvent = streamEventChunk.next();
            String eventString = (String) compressedEvent.getAttribute(this.dataColumnIndex);
            if (!eventString.isEmpty()) {
                ByteArrayInputStream unzippedByteArray;
                if ((Boolean) compressedEvent.getAttribute(this.metaCompressedIndex)) {
                    unzippedByteArray = CompressedEventAnalyticsUtils.decompress(eventString);
                } else {
                    unzippedByteArray = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(eventString));
                }
                Input input = new Input(unzippedByteArray);

                // Suppress checking for obvious uncompressed event string
                @SuppressWarnings("unchecked")
                Map<String, Object> aggregatedEvent = kryoTL.get().readObjectOrNull(input, HashMap.class);
                @SuppressWarnings("unchecked")
                ArrayList<List<Object>> eventsList = (ArrayList<List<Object>>) aggregatedEvent.get(
                        AnalyticsConstants.EVENTS_ATTRIBUTE);
                @SuppressWarnings("unchecked")
                ArrayList<PublishingPayload> payloadsList = (ArrayList<PublishingPayload>) aggregatedEvent.get(
                        AnalyticsConstants.PAYLOADS_ATTRIBUTE);

                String host = (String) aggregatedEvent.get(AnalyticsConstants.HOST_ATTRIBUTE);
                int metaTenantId = (int) compressedEvent.getAttribute(this.metaTenantIdIndex);
                // Iterate over the array of events
                for (int i = 0; i < eventsList.size(); i++) {
                    StreamEvent decompressedEvent = streamEventCloner.copyStreamEvent(compressedEvent);
                    // Create a new event with decompressed fields
                    Object[] decompressedFields = CompressedEventUtils.getFieldValues(
                            new ArrayList<>(this.fields.keySet()), eventsList.get(i), payloadsList, i,
                            compressedEvent.getTimestamp(), metaTenantId, host);
                    complexEventPopulater.populateComplexEvent(decompressedEvent, decompressedFields);
                    decompressedStreamEventChunk.add(decompressedEvent);
                }
            } else {
                throw new SiddhiAppRuntimeException("Error occured while decompressing events. No compressed" +
                        " data found.");
            }
        }
        nextProcessor.process(decompressedStreamEventChunk);
    }

    /**
     * Get attributes which are to be to be populated in the uncompressed message
     *
     * @param attributeExpressionExecutors are the executors of each attributes in the Function
     * @param configReader                 this hold the {@link StreamProcessor} extensions configuration reader.
     * @param siddhiAppContext             Siddhi app runtime context
     */
    @Override
    protected List<Attribute> init(AbstractDefinition inputDefinition,
                                   ExpressionExecutor[] attributeExpressionExecutors, ConfigReader configReader,
                                   SiddhiAppContext siddhiAppContext) {

        this.fields = getOutputFields();
        List<Attribute> outputAttributes = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.fields.entrySet()) {
            String fielname = entry.getKey();
            String fieldType = entry.getValue();
            Attribute.Type type = null;
            if (fieldType.equalsIgnoreCase(TYPE_DOUBLE)) {
                type = Attribute.Type.DOUBLE;
            } else if (fieldType.equalsIgnoreCase(TYPE_FLOAT)) {
                type = Attribute.Type.FLOAT;
            } else if (fieldType.equalsIgnoreCase(TYPE_INTEGER)) {
                type = Attribute.Type.INT;
            } else if (fieldType.equalsIgnoreCase(TYPE_LONG)) {
                type = Attribute.Type.LONG;
            } else if (fieldType.equalsIgnoreCase(TYPE_BOOL)) {
                type = Attribute.Type.BOOL;
            } else if (fieldType.equalsIgnoreCase(TYPE_STRING)) {
                type = Attribute.Type.STRING;
            }
            outputAttributes.add(new Attribute(fielname, type));
        }

        return outputAttributes;
    }

    /**
     * Set index values for each attribute in the incoming compressed message
     */
    @Override
    public void start() {

        for (ExpressionExecutor expressionExecutor : attributeExpressionExecutors) {
            if (expressionExecutor instanceof VariableExpressionExecutor) {
                VariableExpressionExecutor variable = (VariableExpressionExecutor) expressionExecutor;
                String variableName = variable.getAttribute().getName();
                switch (variableName) {
                    case AnalyticsConstants.DATA_COLUMN:
                        this.dataColumnIndex = variable.getPosition();
                        break;
                    case AnalyticsConstants.META_FIELD_COMPRESSED:
                        this.metaCompressedIndex = variable.getPosition();
                        break;
                    case AnalyticsConstants.META_FIELD_TENANT_ID:
                        this.metaTenantIdIndex = variable.getPosition();
                        break;
                    default:
                        break;
                }
            }
        }

        if (this.dataColumnIndex == null || this.metaCompressedIndex == null || this.metaTenantIdIndex == null) {
            throw new SiddhiAppCreationException("Attributes are invalid. Please use exact names as used " +
                    "in the example for the attributes (flowData, meta_compressed and meta_tenantId)");
        }
    }

    /**
     * This will be called only once and this can be used to release
     * the acquired resources for processing.
     * This will be called before shutting down the system.
     */
    @Override
    public void stop() {

    }

    /**
     * Used to collect the serializable state of the processing element, that need to be
     * persisted for reconstructing the element to the same state on a different point of time
     *
     * @return stateful objects of the processing element as an map
     */
    @Override
    public Map<String, Object> currentState() {

        return new HashMap<>();
    }

    /**
     * Used to restore serialized state of the processing element, for reconstructing
     * the element to the same state as if was on a previous point of time.
     *
     * @param state the stateful objects of the processing element as a map.
     *              This is the same map that is created upon calling currentState() method.
     */
    @Override
    public void restoreState(Map<String, Object> state) {

    }
}

