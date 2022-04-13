<!DOCTYPE html>
<#import "/spring.ftl" as spring/>
<html lang="en" class="flex-col">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="<@spring.url '/scripts/download.js'/>"></script>
    <script src="<@spring.url '/scripts/script.js'/>"></script>
    <title>New Schema</title>
</head>
<body class="flex-col bg-dark">
    <div class="alert-box flex-row flex-center bg-white
    text-success fs-12 pos-absolute pos-top pos-left ma-10 pa-10 br-5" id="alert-sv" >
        <span>Saved!</span>
    </div>
    <form class="flex-col br-10 text-light" id="new_schema">
        <div class="pos-sticky pos-top flex-row align-center flex-center w-800 bg-dark">
            <div class="flex-row">
                <button class="btn-save bg-none fs-16 ma-5 text-success pa-0" onclick="process()" type="button">
                    <i class="bi bi-cloud-check-fill"></i>
                </button>
                <input class="ma-5 pa-5 br-5 fs-12" id="schema_name" name="schemaName"
                       value="${schema.sqlSchema.orElse("")}" type="text" placeholder="SQL Schema Name">
            </div>
            <div class="flex-row ma-5 mv-auto">
                <input class="mv-auto" id="is_root_check" name="Database Administrator" value="asRoot" type="checkbox"
                       <#if schema.byRoot>checked</#if>>
                <label class="fs-12" for="is_root_check">DBA</label>
            </div>
        </div>
        <#list schema.tables as table>
            <div class="js-table-block flex-col flex-center ma-10 align-center w-800 br-10 bg-light text-light">
                <div class="align-end flex-row bg-dark" style="border-radius: 0 5px 0 10px">
                    <button class="btn-remove fs-16 ma-5 text-alert pa-0" onclick="removeBlock(this)" type="button">
                        <i class="bi bi-node-minus-fill"></i>
                    </button>
                </div>
                <div class="flex-col flex-center align-center w-600">
                    <input class="align-center fs-10 w-200 pa-5 ma-10 br-5 border-white border-small" type="text" name="tableName"
                           value="${table.tableName}" placeholder="Table Name" required>
                    <span class="align-center w-1 fs-12 ma-10">Columns:</span>
                    <#list table.columns as column>
                        <div class="js-col-block flex-row flex-even ma-5 w-1 align-center">
                            <input class="columnName align-center fs-10 w-1 pa-5 br-5 border-white border-small" type="text"
                                   name="columnName" value="${column.name}" placeholder="Column Name" required>
                            <select class="columnType align-center fs-10 w-1 pa-5 ma-5 br-5 border-white border-small"
                                    name="columnType" required>
                                <option class="pa-5" <#if !column.type?has_content>selected</#if> disabled>
                                    select column type...
                                </option>
                                <#list coltypes as coltype>
                                    <option value="${coltype.toType()}"
                                            <#if column.type?has_content &&
                                            coltype.toType() == column.type.toType()>selected</#if>>
                                        ${coltype.toCasual()}
                                    </option>
                                </#list>
                            </select>
                            <div class="align-center flex-col">
                                <button class="btn-remove fs-14 ma-5 text-alert w-fit pa-0"
                                        onclick="removeBlock(this)" type="button">
                                    <i class="bi bi-trash-fill"></i>
                                </button>
                            </div>
                        </div>
                    </#list>
                    <button class="btn btn-primary align-center w-200 br-5 ma-10 pa-5 fs-14"
                            onclick="newBlock(this)" type="button">
                        <i class="bi bi-plus-lg"></i>
                    </button>
                    <span class="align-center w-1 fs-12 ma-10">Constraints:</span>
                    <#list table.constraints as constraint>
                        <div class="js-con-block flex-row flex-even w-1 ma-5 align-center">
                            <div class="flex-col flex-even w-1 align-center">
                                <div class="w-1 flex-row flex-between align-center">
                                    <input class="w-1 align-center fs-10 pa-5 br-5 border-white border-small" type="text"
                                           name="constraintName" value="${constraint.name.orElse("")}"
                                           placeholder="Constraint Name">
                                    <select class="align-center fs-10 pa-5 ma-5 br-5 border-white border-small"
                                            onchange="toggleReference(this)" name="constraintType" required>
                                        <option class="pa-5" <#if !constraint.type?has_content>selected</#if> disabled>
                                            select constraint type...
                                        </option>
                                        <#list contypes as contype>
                                            <option value="${contype.toType()}"
                                                    <#if constraint.type?has_content &&
                                                    contype.toType() == constraint.type.toType()>selected</#if>>
                                                ${contype.toString()}
                                            </option>
                                        </#list>
                                    </select>
                                    <input class="w-1 align-center fs-10 pa-5 br-5 border-white border-small" type="text"
                                           name="constraintColumn" value="${constraint.column}"
                                           placeholder="Constraint Column" required>
                                </div>
                                <div class="js-reference-block w-1 flex-row flex-between align-center"
                                    <#if constraint.reference.isEmpty()>style="display: none"</#if>>
                                    <input class="w-1 align-center fs-10 ma-5 pa-5 br-5 border-white border-small"
                                           type="text" name="referenceTable" placeholder="Reference Table"
                                           value="<#if constraint.reference.isPresent()>
                                                ${constraint.reference.get().referenceTable}</#if>"
                                           style="margin-left: 0" required>
                                    <input class="w-1 align-center fs-10 ma-5 pa-5 br-5 border-white border-small"
                                           type="text" name="referenceColumn" placeholder="Reference Column"
                                           value="<#if constraint.reference.isPresent()>
                                                ${constraint.reference.get().referenceColumn}</#if>"
                                           style="margin-right: 0" required>
                                </div>
                            </div>
                            <div class="align-center flex-col">
                                <button class="btn-remove fs-14 ma-5 text-alert w-fit pa-0"
                                        onclick="removeBlock(this)" type="button">
                                    <i class="bi bi-trash-fill"></i>
                                </button>
                            </div>
                        </div>
                    </#list>
                    <button class="btn btn-primary align-center w-200 br-5 ma-10 pa-5 fs-14"
                            onclick="newBlock(this)" type="button">
                        <i class="bi bi-plus-lg"></i>
                    </button>
                </div>
            </div>
        </#list>
        <button class="btn btn-primary align-center w-200 br-5 ma-10 pa-5 fs-16"
                onclick="newBlock(this)" type="button">
            <i class="bi bi-node-plus-fill"></i>
        </button>
        <div class="pos-sticky pos-bottom flex-row align-center w-800 flex-center bg-dark">
            <button class="btn btn-success align-center w-200 br-5 ma-10 pa-5 fs-14"
                    id="download_script" onclick="downloadScript()" type="button">
                <i class="bi bi-download"></i>
            </button>
            <button class="btn btn-danger align-center w-200 br-5 ma-10 pa-5 fs-14"
                    onclick="resetSession()" type="button">
                <i class="bi bi-arrow-clockwise"></i>
            </button>
        </div>
    </form>
</body>
</html>