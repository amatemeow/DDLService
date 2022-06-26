function newBlock(element) {
    let parent = element.parentNode;
    let div = document.createElement('div');
    let sample = element.previousElementSibling;
    parent.insertBefore(div, sample.nextSibling);
    div.setAttribute('class', sample.className);
    div.innerHTML = sample.innerHTML;
}

function removeBlock(element) {
    let parent = element.parentNode.parentNode;
    if (parent.parentNode.getElementsByClassName(parent.classList).length === 1) {
        $(parent).toggle(false);
    } else {
        parent.remove();
    }
}

async function checkFields() {
    let filled = true;

    let requiredFields = document.getElementById('new_schema').querySelectorAll('[required]')

    requiredFields.forEach(i => {
        if (!filled) return;
        if ($(i).is(':visible') && !i.value) filled = false;
    });

    if (!filled) {
        requiredFields.forEach(i => {
            if (!$(i).val()) {
                i.classList.remove('border-none');
                i.classList.add('border-danger');
            } else {
                i.classList.remove('border-danger');
                i.classList.add('border-none');
            }
        });
        alertOnError([null, 400, 'Please, fill all required fields!']);
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

function toggleConstraintOptional(element) {
    let parent = element.parentNode.parentNode;
    if ($(element).val() === 'FOREIGN_KEY') {
        $(parent).find('.js-reference-block').toggle(true);
        $(parent).find('.js-check-block').toggle(false);
    } else if ($(element).val() === 'CHECK') {
        $(parent).find('.js-check-block').toggle(true);
        $(parent).find('.js-reference-block').toggle(false);
    } else {
        $(parent).find('.js-reference-block').toggle(false);
        $(parent).find('.js-check-block').toggle(false);
    }
}

async function process() {

    let tables = document.getElementsByClassName('js-table-block');

    let raw = {};
    raw.sqlSchema = document.getElementById('schema_name').value || null;
    raw.byRoot = $(document.getElementById('is_root_check')).is(':checked');
    raw.user = '';
    raw.schemaName = '';
    let rawtables = [];

    [].forEach.call(tables, t => {
        let columns = $(t).find('.js-col-block');
        let rawcols = [];
        [].forEach.call(columns, c => {
            rawcols.push({
                'name': $(c).find('[name="columnName"]').val(),
                'type': $(c).find('[name="columnType"]').val()
            });
        });
        let constraints = $(t).find('.js-con-block');
        let rawcons = [];
        [].forEach.call(constraints, c => {
            rawcons.push({
                'name': $(c).find('[name="constraintName"]').val() || null,
                'type': $(c).find('[name="constraintType"]').val(),
                'column': $(c).find('[name="constraintColumn"]').val(),
                'reference': !$(c).find('.js-reference-block').is(':visible') ? null :
                    {
                        'referenceColumn': $(c).find('[name="referenceColumn"]').val(),
                        'referenceTable': $(c).find('[name="referenceTable"]').val()
                    },
                'check': !$(c).find('.js-check-block').is(':visible') ? null :
                    {
                        'type': $(c).find('[name="checkType"]').val(),
                        'expression': $(c).find('[name="checkExpression"]').val()
                    }
            });
        });
        rawtables.push({
            'tableName': $(t).find('[name="tableName"]').val(),
            'columns': rawcols, 'constraints': rawcons
        });
    });

    raw.tables = rawtables;
    let json = JSON.stringify(raw);

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
    if (!(await checkFields())) return;
    await process();

    const response = fetch('/api/download', {
        method: 'GET'
    }).then(resp => {
        let flag = false
        if (resp.status === 200) {
            flag = true
        }
        return [flag, resp.status, resp.blob()]
    }).then(data => {
        if (data[0]) return data[2]
        else alertOnError(data)
    }).then(blob => {
        if (blob) download(blob, 'scriptDDL.sql')
    });
}

async function alertOnError(data) {
    let el = document.querySelector("#alert-error");
    let msg = data[2] instanceof Promise ? await (await data[2]).text() : data[2];
    el.innerHTML = "<p>" + "Status: " + data[1] + "</p>" + "<p>" + "Message: " + msg + "</p>";
    await raiseAlert('alert-error', 5000);
    el.innerHTML = "";
}

async function resetSession() {
    const response = await fetch('/api/dropSession', {method: 'POST'});
    window.location.reload();
}