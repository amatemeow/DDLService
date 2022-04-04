function newBlock(element) {
    let parent = element.parentNode;
    let div = document.createElement('div');
    let sample = element.previousElementSibling;
    parent.insertBefore(div, sample.nextSibling);
    div.setAttribute('class', sample.className);
    div.innerHTML = sample.innerHTML;
}

function process() {
    let schema = document.getElementById('new_schema');
    let filled = true;

    schema.querySelectorAll('[required]').forEach(i => {
       if (!filled) return;
       if (!i.value) filled = false;
    });

    if (!filled) {
        alert('Please, fill all required fields!');
        return;
    }

    let tables = document.getElementsByClassName('new_table_block');

    let raw = {};
    raw.sqlSchema = document.getElementById('schema_name').value;
    raw.byRoot = $(document.getElementById('is_root_check')).is(':checked');
    raw.user = 'exampleUser';
    raw.schemaName = 'CoolSchema';
    let rawtables = [];

    [].forEach.call(tables, t => {
        let columns = document.getElementsByClassName('new-col-block');
        let rawcols = [];
        [].forEach.call(columns, c => {
            rawcols.push({'name': $(c).find('[name="columnName"]').val(),
                'type': $(c).find('[name="columnType"]').val()});
        });
        let constraints = document.getElementsByClassName('new-con-block');
        let rawcons = [];
        [].forEach.call(constraints, c => {
            rawcons.push({'name': $(c).find('[name="constraintName"]').val(),
                'type': $(c).find('[name="constraintType"]').val(),
                'column': $(c).find('[name="constraintColumn"]').val(),
                'reference': {
                    'referenceColumn': $(c).find('[name="referenceColumn"]').val(),
                    'referenceTable': $(c).find('[name="referenceTable"]').val()}});
        });
        rawtables.push({'tableName': $(t).find('[name="tableName"]').val(),
            'columns': rawcols, 'constraints': rawcons})
    });

    raw.tables = rawtables;
    let json = JSON.stringify(raw);

    // let xhr = new XMLHttpRequest();
    // xhr.open('POST', '/api/json/download');
    // xhr.setRequestHeader("Content-type", "application/json")
    // //set the reponse type to blob since that's what we're expecting back
    // xhr.responseType = 'blob';
    // xhr.onload = e => {
    //     if (this.status == 200) {
    //         // Create a new Blob object using the response data of the onload object
    //         var blob = new Blob([this.response], {type: 'application/sql'});
    //         //Create a link element, hide it, direct it towards the blob, and then 'click' it programatically
    //         let a = document.createElement("a");
    //         a.style = "display: none";
    //         document.body.appendChild(a);
    //         //Create a DOMString representing the blob and point the link element towards it
    //         let url = window.URL.createObjectURL(blob);
    //         a.href = url;
    //         a.download = 'ddlScript.sql';
    //         //programatically click the link to trigger the download
    //         a.click();
    //         //release the reference to the file by revoking the Object URL
    //         window.URL.revokeObjectURL(url);
    //     }else{
    //         //deal with your error state here
    //     }
    // };
    // xhr.send(json);
        console.log(raw);

        const response = fetch('/api/json/download', {
            method: 'POST',
            body: json, // string or object
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(resp => {return resp.blob()}).then(blob => {download(blob, 'scriptDDL.sql')});
}