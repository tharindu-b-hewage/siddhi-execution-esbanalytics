# API Docs - v1.0.0-SNAPSHOT

## Esbanalytics

### decompress *<a target="_blank" href="https://wso2.github.io/siddhi/documentation/siddhi-4.0/#stream-processor">(Stream Processor)</a>*

<p style="word-wrap: break-word">This extension decompress any compressed analytics events coming from WSO2 Enterprice Integrator</p>

<span id="syntax" class="md-typeset" style="display: block; font-weight: bold;">Syntax</span>
```
esbAnalytics:decompress(<BOOL> meta.compressed, <INT> meta.tenant.id, <STRING> message.id, <STRING> flow.data)
```

<span id="query-parameters" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">QUERY PARAMETERS</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Default Value</th>
        <th>Possible Data Types</th>
        <th>Optional</th>
        <th>Dynamic</th>
    </tr>
    <tr>
        <td style="vertical-align: top">meta.compressed</td>
        <td style="vertical-align: top; word-wrap: break-word">Compressed state of the message</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">BOOL</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">meta.tenant.id</td>
        <td style="vertical-align: top; word-wrap: break-word">Tenant id</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">INT</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">message.id</td>
        <td style="vertical-align: top; word-wrap: break-word">Message id</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
    <tr>
        <td style="vertical-align: top">flow.data</td>
        <td style="vertical-align: top; word-wrap: break-word">Compressed stream events chunk</td>
        <td style="vertical-align: top"></td>
        <td style="vertical-align: top">STRING</td>
        <td style="vertical-align: top">No</td>
        <td style="vertical-align: top">No</td>
    </tr>
</table>
<span id="extra-return-attributes" class="md-typeset" style="display: block; font-weight: bold;">Extra Return Attributes</span>
<table>
    <tr>
        <th>Name</th>
        <th style="min-width: 20em">Description</th>
        <th>Possible Types</th>
    </tr>
    <tr>
        <td style="vertical-align: top">messageFlowId</td>
        <td style="vertical-align: top; word-wrap: break-word">Statistic tracing id for the message flow</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">host</td>
        <td style="vertical-align: top; word-wrap: break-word">-</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">hashCode</td>
        <td style="vertical-align: top; word-wrap: break-word">HashCode of the reporting component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">componentName</td>
        <td style="vertical-align: top; word-wrap: break-word">Name of the component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">componentType</td>
        <td style="vertical-align: top; word-wrap: break-word">Component type of the component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">componentIndex</td>
        <td style="vertical-align: top; word-wrap: break-word">-</td>
        <td style="vertical-align: top">INT</td>
    </tr>
    <tr>
        <td style="vertical-align: top">componentId</td>
        <td style="vertical-align: top; word-wrap: break-word">Unique Id of the reporting component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">startTime</td>
        <td style="vertical-align: top; word-wrap: break-word">Start time of the event-</td>
        <td style="vertical-align: top">LONG</td>
    </tr>
    <tr>
        <td style="vertical-align: top">endTime</td>
        <td style="vertical-align: top; word-wrap: break-word">EndTime of the Event</td>
        <td style="vertical-align: top">LONG</td>
    </tr>
    <tr>
        <td style="vertical-align: top">duration</td>
        <td style="vertical-align: top; word-wrap: break-word">-</td>
        <td style="vertical-align: top">LONG</td>
    </tr>
    <tr>
        <td style="vertical-align: top">beforePayload</td>
        <td style="vertical-align: top; word-wrap: break-word">Payload before mediation by the component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">afterPayLoad</td>
        <td style="vertical-align: top; word-wrap: break-word">Payload after mediation by the component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">contextPropertyMap</td>
        <td style="vertical-align: top; word-wrap: break-word">Message context properties for the component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">transportPropertyMap</td>
        <td style="vertical-align: top; word-wrap: break-word">-Transport properties for the component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">children</td>
        <td style="vertical-align: top; word-wrap: break-word">Children List for the component</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">entryPoint</td>
        <td style="vertical-align: top; word-wrap: break-word">-</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">entryPointHashcode</td>
        <td style="vertical-align: top; word-wrap: break-word">-</td>
        <td style="vertical-align: top">STRING</td>
    </tr>
    <tr>
        <td style="vertical-align: top">faultCount</td>
        <td style="vertical-align: top; word-wrap: break-word">-</td>
        <td style="vertical-align: top">INT</td>
    </tr>
    <tr>
        <td style="vertical-align: top">metaTenantId</td>
        <td style="vertical-align: top; word-wrap: break-word">-</td>
        <td style="vertical-align: top">INT</td>
    </tr>
    <tr>
        <td style="vertical-align: top">timestamp</td>
        <td style="vertical-align: top; word-wrap: break-word">-</td>
        <td style="vertical-align: top">LONG</td>
    </tr>
</table>

<span id="examples" class="md-typeset" style="display: block; font-weight: bold;">Examples</span>
<span id="example-1" class="md-typeset" style="display: block; color: rgba(0, 0, 0, 0.54); font-size: 12.8px; font-weight: bold;">EXAMPLE 1</span>
```
define stream inputStream(meta_compressed bool, meta_tenantId int, messageId string, flowData string); @info( name = 'query') from inputStream#esbAnalytics:decompress(meta_compressed, meta_tenantId, messageId, flowData) insert all events into outputStream;
```
<p style="word-wrap: break-word">This query uses the incoming esb analytics message to produce decompressed esb analytics events.</p>

