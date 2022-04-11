function newBlock(element) {
    let parent = element.parentNode;
    let div = document.createElement('div');
    let sample = element.previousElementSibling;
    parent.insertBefore(div, sample.nextSibling);
    div.setAttribute('class', sample.className);
    div.innerHTML = sample.innerHTML;
}

function removeBlock(element) {
    element.parentNode.parentNode.remove();
}

function checkFields() {
    let filled = true;

    document.getElementById('new_schema').querySelectorAll('[required]').forEach(i => {
        if (!filled) return;
        if ($(i).is(':visible') && !i.value) filled = false;
    });

    if (!filled) {
        alert('Please, fill all required fields!');
    }
    return filled;
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function raiseAlert(id, duration) {
    let alertBox = document.getElementById(id);
    alertBox.style.display = 'block';
    window.setTimeout(function(){
        alertBox.style.opacity = '1';
        alertBox.style.transform = 'scale(1)';
    },400);
    await sleep(duration);
    alertBox.style.opacity = '0';
    alertBox.style.transform = 'scale(0)';
    window.setTimeout(function(){
        alertBox.style.display = 'none';
    },400);
}

function toggleReference(element) {
    if ($(element).val() === 'FOREIGN_KEY') {
        $(element.parentNode.parentNode).find('.new-reference-block').toggle(true);
    } else {
        $(element.parentNode.parentNode).find('.new-reference-block').toggle(false);
    }
}

async function process() {
    let schema = document.getElementById('new_schema');

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
            rawcols.push({
                'name': $(c).find('[name="columnName"]').val(),
                'type': $(c).find('[name="columnType"]').val()
            });
        });
        let constraints = document.getElementsByClassName('new-con-block');
        let rawcons = [];
        [].forEach.call(constraints, c => {
            rawcons.push({
                'name': $(c).find('[name="constraintName"]').val(),
                'type': $(c).find('[name="constraintType"]').val(),
                'column': $(c).find('[name="constraintColumn"]').val(),
                'reference': !$(c).find('.new-reference-block').is(':visible') ? null :
                    {
                    'referenceColumn': $(c).find('[name="referenceColumn"]').val(),
                    'referenceTable': $(c).find('[name="referenceTable"]').val()
                }
            });
        });
        rawtables.push({
            'tableName': $(t).find('[name="tableName"]').val(),
            'columns': rawcols, 'constraints': rawcons
        })
    });

    raw.tables = rawtables;
    let json = JSON.stringify(raw);
    console.log(raw);

    const response = await fetch('/api/schema/new', {
        method: 'POST',
        body: json,
        headers: {
            'Content-Type': 'application/json'
        }
    });

    raiseAlert('alert-sv', 2000);
}

async function downloadScript() {
    if (!checkFields()) return;
    await process();

    const response = fetch('/api/download', {
        method: 'GET'
    }).then(resp => {
        return resp.blob()
    }).then(blob => {
        download(blob, 'scriptDDL.sql')
    });
}

async function resetSession() {
    const response = await fetch('/api/dropSession', {method: 'POST'});
    window.location.reload();
}