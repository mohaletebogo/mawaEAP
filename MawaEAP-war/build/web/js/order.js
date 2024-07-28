/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

order = {
    formData: null,
    create: function () {
        window.location.href = "../Policy/New.html";
    },
    cancel: function () {
        window.location.href = "../Policy/View.html";
        mawa.setUserAction("view");
    },
    search: function () {
        var screenValid = $("#policyForm").validate({
            onsubmit: false,
            rules: {
                idNumber: {
                    required: true,
                    //minlength: 13,
                    //maxlength: 13,
                    digits: true,
                    validateID: true
                }
            }
        }).form();
        if (screenValid === true) {
            policy.formData = $("#policyForm").serializeArray();
            policy.formData.push({"name": "userAction", "value": "search"});
            sessionStorage.action = "search";
            policy.serverCall("search", policy.formData);

        }

    },
    view: function (policyNo) {
        policy.setCurrent(policyNo);
        sessionStorage.action = "view";
        window.location.href = "../Policy/View.html";
    },
    dependent: function () {
        sessionStorage.action = "create";
        window.location.href = "../Dependant/New.html";
    },
    payment: function () {
        sessionStorage.action = "create";
        window.location.href = "../Payment/New.html";
    },
    claim: function () {
        sessionStorage.action = "create";
        window.location.href = "../Claim/New.html";
    },
    populatePolicy: function (data) {
        $("#policyNo").val(data.policyNo);
        $("#customer").val(data.customer);

        sessionStorage.customerFullName = data.customer;
        $("#salesRepresentative").val(data.salesRepresentative);
        $("#salesArea").val(data.salesArea);
        $("#product").val(data.productCode);
        $("#monthlyPremium").val(data.premium);
        $("#dateJoined").val(data.dateJoined);
        $("#dateEffective").val(data.dateEffective);
        $("#status").val(data.status);
        $("#statusDate").val(data.statusDate);

        var d = $('#dependentList').DataTable().clear().draw();
        $.each(data.dependents, function (index, row) {
            var fullname = row.lastName + " " + row.firstName + " " + row.middleName;
            fullname = fullname.replace("undefined", "");
            var idNumber = "Not Supplied";
            if (row.idNumber !== null && row.idNumber !== undefined) {
                idNumber = row.idNumber;
            }
            var paymentButton = "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal'>Payment</button>";
//            var viewButton = "<button type='button' class='btn btn-primary' onClick='policy.view(" + row.policyNo + ")'>View</button>";
            d.row.add([row.id, idNumber, fullname, row.dateAdded, row.dateEffective, row.status, row.statusDate, row.statusReason]).draw(false);
        });

        var p = $('#paymentList').DataTable().clear().draw();
        $.each(data.payments, function (index, row) {
            p.row.add([row.paymentDate, row.paymentType, row.receivedBy, row.extReceiptNo, row.amount]).draw(false);
        });

        var c = $('#claimList').DataTable().clear().draw();
        $.each(data.claims, function (index, row) {
            c.row.add([row.claimNo, '', '', '', '', '', '']).draw(false);

        });
    },
    setCurrent: function (policyNo) {
        sessionStorage.currentOrder = policyNo;
    },
    save: function (action) {
        var screenValid = $("#policyForm").validate({
            onsubmit: false,
            rules: {
                receiptNumber: {
                    required: true,
                    minlength: 5,
//                    maxlength: 10,
                    digits: true
                },
                idNumber: {
                    required: true,
                    //minlength: 13,
                    //maxlength: 13,
                    digits: true,
                    validateID: true
                }
            }
        }).form();

        //var idValid = mawa.validateIDNumber($("#idNumber").val());

        if (screenValid === true) {
            policy.formData = $("#policyForm").serializeArray();
            policy.formData.push({"name": "userAction", "value": "create"});
            policy.formData.push({"name": "customerNo", "value": sessionStorage.currentCustomer});
            policy.serverCall(action, policy.formData);
            sessionStorage.action = action;
        }
    },
    fillResult: function (data) {
        var t = $('#policyList').DataTable().clear().draw();
        $.each(data, function (index, row) {
            var paymentButton = "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal'>Payment</button>";
            var viewButton = "<a href='#' onClick='policy.view(" + row.policyNo + ")'>" + row.policyNo + "</a>";
            var button = '<div class="form-group" style="position:relative;left:20px;"><div class="dropdown"><button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Action<span class="caret"></span></button><ul class="dropdown-menu"><li><a onclick="policy.dependent()">Add New</a></li> <li><a href="../Dependant/Replace.html">Replace</a></li></ul></div></div>'
            t.row.add([viewButton, row.customer, row.productCode, row.status]).draw(false);
        });

    },
    serverCall: function (action, params) {
        $.blockUI();
        try {
            $.ajax({
                url: mawa.url + "/PolicyServlet",
                type: "POST",
                dataType: 'jsonp',
                crossDomain: true,
                data: params,
                async: false,
                timeout: 10000
            }).done(function (dataR, textStatus, jqXHR) {
                if (mawa.checkErrors(dataR.messages) !== true) {
                    switch (action) {
                        case "search" :
                            policy.fillResult(dataR.value.data);
                            break;
                        case "create":
                            policy.view(dataR.value.data);
                            break;
                        case "get":
                            policy.populatePolicy(dataR.value.data);
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
    mawa.getFieldOptions();

    $('#itemList').DataTable({
        "pagingType": "full_numbers",
        "bLengthChange": false,
        "bPaginate": true,
        "pageLength": 9,
        "autoWidth": true,
        "bFilter": false
    });
    $('#dependentList').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });
    $('#claimList').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });
    $('#paymentList').dataTable({
        "info": false,
        "ordering": false,
        "bFilter": false,
        "bLengthChange": false,
        "bPaginate": false
    });
    $("input[name='extReceipt']").click(function () {
        if ($(this).val() === 'Yes') {
            $("#extReceiptTab").show();
        } else {
            $("#extReceiptTab").hide();
        }
    });

    $("#productCode").change(function () {
        var amount = mawa.getProductPrice($("#productCode").val());
        $("#paymentAmount").val(amount);

    });
    switch (sessionStorage.action) {
        case "create":
            $("#fullName").val(sessionStorage.customerFullName);
            break;
        case "view":
            var data = [];
            data.push({"name": "userAction", "value": "get"});
            data.push({"name": "policyNo", "value": sessionStorage.currentOrder});
            policy.serverCall("get", data);
            break;
    }

    mawa.getProducts("FUNERALPOLICY");


});


