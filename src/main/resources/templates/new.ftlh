<!DOCTYPE html>
<#import "/spring.ftl" as spring/>
<html lang="en" class="flex-col">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="<@spring.url '/scripts/download.js'/>"></script>
    <script src="<@spring.url '/scripts/script.js'/>"></script>
    <title>New Schema</title>
</head>
<body class="flex-col">
    <form id="new_schema" class="flex-col br-10 st-container-b">
        <div class="flex-row align-center flex-center w-800">
            <div class="flex-row">
                <input id="schema_name" type="text" name="schemaName" placeholder="SQL Schema Name"
                       required class="ma-5 pa-5 br-5 fs-12">
            </div>
            <div class="flex-row ma-5 mv-auto">
                <input id="is_root_check" type="checkbox" name="Database Administrator" value="asRoot">
                <label for="is_root_check" class="fs-12">As DBA</label>
            </div>
        </div>
        <div class="new_table_block flex-col flex-center ma-10 align-center w-800 br-10 st-container-f">
            <input type="text" name="tableName" placeholder="Table Name" required
                   class="align-center w-400 fs-10 pa-5 ma-10 br-5 b-white b-s">
            <span class="align-center w-400 fs-12 ma-10">Columns:</span>
            <div class="new-col-block flex-row flex-even ma-5 w-400 align-center">
                <input type="text" name="columnName" placeholder="Column Name" required
                       class="columnName align-center w-1 fs-10 pa-5 br-5 b-white b-s">
                <select name="columnType" required class="columnType align-center w-1 fs-10 pa-5 ma-5 br-5 b-white b-s">
                    <option class="pa-5" selected disabled>select column type...</option>
                    <#list coltypes as coltype>
                        <option value="${coltype.toType()}">${coltype.toString()}</option>
                    </#list>
                </select>
            </div>
            <button type="button" onclick="newBlock(this)"
                    class="new-btn align-center w-200 br-5 dw-btn ma-10 pa-5 fs-16">+</button>
            <span class="align-center w-400 fs-12 ma-10">Constraints:</span>
            <div class="new-con-block flex-col flex-even w-400 ma-5 align-center">
                <div class="flex-row flex-between w-1 align-center">
                    <input type="text" name="constraintName" placeholder="Constraint Name" required
                           class="align-center w-1 fs-10 ma-5 pa-5 br-5 b-white b-s">
                    <select name="constraintType" required class="align-center w-1 fs-10 pa-5 ma-5 br-5 b-white b-s">
                        <option class="pa-5" selected disabled>select constraint type...</option>
                        <#list contypes as contype>
                            <option value="${contype.toType()}">${contype.toString()}</option>
                        </#list>
                    </select>
                    <input type="text" name="constraintColumn" placeholder="Constraint Column" required
                           class="align-center w-1 fs-10 ma-5 pa-5 br-5 b-white b-s">
                </div>
                <div class="flex-row flex-between w-1 align-center">
                    <input type="text" name="referenceTable" placeholder="Reference Table" required
                           class="align-center w-1 fs-10 ma-5 pa-5 br-5 b-white b-s">
                    <input type="text" name="referenceColumn" placeholder="Reference Column" required
                           class="align-center w-1 fs-10 ma-5 pa-5 br-5 b-white b-s">
                </div>
            </div>
            <button type="button" onclick="newBlock(this)"
                    class="new-btn align-center w-200 br-5 dw-btn ma-10 pa-5 fs-16">+</button>
        </div>
        <button type="button" onclick="newBlock(this)"
                class="new-btn align-center w-200 br-5 dw-btn ma-10 pa-5 fs-12">Add Table</button>
        <div class="flex-row align-center w-800 flex-center">
            <button id="download_script" type="button" onclick="process()"
                    class="align-center w-200 br-5 dw-btn ma-10 pa-5 fs-14">Download</button>
            <button type="reset" class="align-center w-200 br-5 dw-btn ma-10 pa-5 fs-14">Reset</button>
        </div>
    </form>
</body>
</html>