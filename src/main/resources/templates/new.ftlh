<!DOCTYPE html>
<#import "/spring.ftl" as spring/>
<html lang="en" class="flex-col">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="<@spring.url '/scripts/download.js'/>"></script>
    <script src="<@spring.url '/scripts/script.js'/>"></script>
    <title>New Schema</title>
</head>
<body class="flex-col bg-dark">
    <div id="alert-sv" class="alert-box flex-row flex-center bg-white
    text-success fs-12 absolute top left ma-10 pa-10 br-5">
        <span>Saved!</span>
    </div>
    <form id="new_schema" class="flex-col br-10 text-light">
        <div class="sticky top flex-row align-center flex-center w-800 bg-dark">
            <div class="flex-row">
                <button type="button" onclick="process()" class="dw-btn bg-none sv-btn fs-16 ma-5 text-success pa-0">
                    <i class="bi bi-cloud-check-fill"></i>
                </button>
                <input id="schema_name" type="text" name="schemaName" placeholder="SQL Schema Name"
                       required value="${schema.sqlSchema.orElse("")}" class="ma-5 pa-5 br-5 fs-12">
            </div>
            <div class="flex-row ma-5 mv-auto">
                <input id="is_root_check" type="checkbox" name="Database Administrator" value="asRoot"
                       <#if schema.byRoot>checked</#if>>
                <label for="is_root_check" class="fs-12">DBA</label>
            </div>
        </div>
        <#list schema.tables as table>
            <div class="new_table_block flex-col flex-center ma-10 align-center w-800 br-10 bg-light text-light">
                <div class="align-end flex-row bg-dark" style="border-radius: 0 10px 0 10px">
                    <button type="button" onclick="removeBlock(this)"
                            class="rm-btn fs-16 ma-5 text-alert pa-0">
                        <i class="bi bi-node-minus-fill"></i>
                    </button>
                </div>
                <div class="flex-col flex-center align-center w-600">
                    <input type="text" name="tableName" placeholder="Table Name" required value="${table.tableName}"
                           class="align-center fs-10 w-200 pa-5 ma-10 br-5 b-white b-s">
                    <span class="align-center w-1 fs-12 ma-10">Columns:</span>
                    <#list table.columns as column>
                        <div class="new-col-block flex-row flex-even ma-5 w-1 align-center">
                            <input type="text" name="columnName" placeholder="Column Name" required value="${column.name}"
                                   class="columnName align-center fs-10 w-1 pa-5 br-5 b-white b-s">
                            <select name="columnType" required class="columnType align-center fs-10 w-1 pa-5 ma-5 br-5 b-white b-s">
                                <option class="pa-5" <#if !column.type?has_content>selected</#if> disabled>select column type...</option>
                                <#list coltypes as coltype>
                                    <option value="${coltype.toType()}"
                                            <#if column.type?has_content &&
                                            coltype.toType() == column.type.toType()>selected</#if>>
                                        ${coltype.toCasual()}</option>
                                </#list>
                            </select>
                            <div class="align-center flex-col">
                                <button type="button" onclick="removeBlock(this)" class="rm-btn fs-14 ma-5 text-alert w-fit pa-0">
                                    <i class="bi bi-trash-fill"></i>
                                </button>
                            </div>
                        </div>
                    </#list>
                    <button type="button" onclick="newBlock(this)"
                            class="new-btn align-center w-100 br-5 dw-btn ma-10 pa-5
                     fs-14 text-light bg-blue hv-blue">
                        <i class="bi bi-plus-lg"></i>
                    </button>
                    <span class="align-center w-1 fs-12 ma-10">Constraints:</span>
                    <#list table.constraints as constraint>
                        <div class="new-con-block flex-row flex-even w-1 ma-5 align-center">
                            <div class="flex-col flex-even w-1 align-center">
                                <div class="w-1 flex-row flex-between align-center">
                                    <input type="text" name="constraintName" placeholder="Constraint Name"
                                           class="w-1 align-center fs-10 pa-5 br-5 b-white b-s"
                                           value="${constraint.name.get()}">
                                    <select name="constraintType" required class="align-center fs-10 pa-5 ma-5 br-5 b-white b-s"
                                        onchange="toggleReference(this)">
                                        <option class="pa-5" <#if !constraint.type?has_content>selected</#if> disabled>
                                            select constraint type...</option>
                                        <#list contypes as contype>
                                            <option value="${contype.toType()}"
                                                    <#if constraint.type?has_content &&
                                                    contype.toType() == constraint.type.toType()>selected</#if>>
                                                ${contype.toString()}</option>
                                        </#list>
                                    </select>
                                    <input type="text" name="constraintColumn" placeholder="Constraint Column" required
                                           class="w-1 align-center fs-10 pa-5 br-5 b-white b-s"
                                           value="${constraint.column}">
                                </div>
                                <div class="w-1 flex-row flex-between align-center new-reference-block"
                                    <#if constraint.reference.isEmpty()>style="display: none"</#if>>
                                    <input type="text" name="referenceTable" placeholder="Reference Table" required
                                           class="w-1 align-center fs-10 ma-5 pa-5 br-5 b-white b-s"
                                           style="margin-left: 0" value="<#if constraint.reference.isPresent()>
                                                ${constraint.reference.get().referenceTable}<#else></#if>">
                                    <input type="text" name="referenceColumn" placeholder="Reference Column" required
                                           class="w-1 align-center fs-10 ma-5 pa-5 br-5 b-white b-s"
                                           style="margin-right: 0" value="<#if constraint.reference.isPresent()>
                                                ${constraint.reference.get().referenceColumn}<#else></#if>">
                                </div>
                            </div>
                            <div class="align-center flex-col">
                                <button type="button" onclick="removeBlock(this)" class="rm-btn fs-14 ma-5 text-alert w-fit pa-0">
                                    <i class="bi bi-trash-fill"></i>
                                </button>
                            </div>
                        </div>
                    </#list>
                    <button type="button" onclick="newBlock(this)"
                            class="new-btn align-center w-100 br-5 dw-btn ma-10 pa-5
                    fs-14 text-light bg-blue hv-blue">
                        <i class="bi bi-plus-lg"></i>
                    </button>
                </div>
            </div>
        </#list>
        <button type="button" onclick="newBlock(this)"
                class="new-btn align-center w-200 br-5 dw-btn ma-10 pa-5 fs-16
                 text-light bg-blue hv-blue">
            <i class="bi bi-node-plus-fill"></i>
        </button>
        <div class="flex-row align-center w-800 flex-center">
            <button id="download_script" type="button" onclick="downloadScript()"
                    class="align-center w-100 br-5 dw-btn ma-10 pa-5 fs-14 text-light
                     bg-success hv-success">
                <i class="bi bi-download"></i>
            </button>
            <button type="button" onclick="resetSession()" class="align-center w-100 br-5 dw-btn ma-10 pa-5 fs-14
             text-light bg-alert hv-alert">
                <i class="bi bi-arrow-clockwise"></i>
            </button>
        </div>
    </form>
</body>
</html>