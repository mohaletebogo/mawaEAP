/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

payment = {
    orderSelectionDialog: null,
    cancel: function () {
        sessionStorage.action = "view";
        window.location.href = "../Policy/View.html";
    },
    search: function () {
        if ($('#idNumber').val().length === '') {

        }
        $("#selectOrder").dialog("open");
    },
    save: function (action) {
        var screenValid = $("#paymentForm").validate({
            onsubmit: false,
            rules: {
                idNumber: {
                    required: true,
                    minlength: 13,
                    maxlength: 13,
                    digits: true,
                    validateID: true
                }
            }
        }).form();
        if (screenValid) {
            var data = $("#paymentForm").serializeArray();
            payment.serverCall(action, data);

        }
    },
    serverCall: function (action, params) {
        params.push({"name": "userAction", "value": action});
        $.blockUI();
        try {
            $.ajax({
                url: mawa.url + "/PaymentServlet",
                type: "POST",
                dataType: 'jsonp',
                crossDomain: true,
                data: params,
                async: false,
                timeout: 10000
            }).done(function (dataR, textStatus, jqXHR) {
                if (mawa.checkErrors(dataR.messages) !== true) {
                    switch (action) {
                        case "create" :
                            payment.cancel();
                            break;
                        case "save":
                            break;
                        case "search":
                            customer.populateResults(dataR.value.data);
                            break;
                        case "get":
                            customer.populateCustomerView(dataR.value.data);
                            break;
                        }
                }
                $.unblockUI();

                $.each(dataR.messages, function (index, row) {
                    mawa.showMessage(row.type, row.message);
                });

            }).fail(function (jqXHR, textStatus, errorThrown) {

                $.unblockUI();
                mawa.showMessage('E', errorThrown);

            }).always(function () {

            });
        } catch (err) {
            mawa.showMessage('E', 'Server not available');

        }
    }


};

$(document).ready(function () {
    $("#tabs").tabs();
    mawa.convertToCurrency("amount");
    mawa.getFieldOptions();
    switch (sessionStorage.action) {
        case "create":
            $("#referenceNo").val(sessionStorage.currentOrder);
            $("#customer").val(sessionStorage.customerFullName);
            break;
        case "view":
            var data = [];
            data.push({"name": "userAction", "value": "get"});
            data.push({"name": "policyNo", "value": sessionStorage.currentOrder});
            sessionStorage.action = "get";
            policy.serverCall(data);
            break;
    }
    $('#result-list').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });

    $('#order-list').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });
    $("#selectOrder").dialog({
        title: "Select Order",
        autoOpen: false,
        width: 800,
        height: 400
    });
    // $("input[name='radio']:checked").val()
    $("input[name='extReceipt']").click(function () {
        if ($(this).val() === 'Yes') {
            $("#extReceiptTab").show();
        } else {
            $("#extReceiptTab").hide();
        }
    });

});
