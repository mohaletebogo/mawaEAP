/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


claim = {
    cancel: function () {
        sessionStorage.action = "view";
        window.location.href = "../Policy/View.html";
    },
    view: function () {
        window.location.href = "../Claim/View.html";
    },
    getDependents: function () {
        var data = [];
        data.push({"name": "userAction", "value": "getDependents"});
        data.push({"name": "policyNo", "value": sessionStorage.currentTransaction});
        claim.serverCall("getDependents", data);
    },
    save: function (process) {
        var screenValid = $("#claimForm").validate({
            onsubmit: false
        }).form();
        if (screenValid) {
            var data = $("#claimForm").serializeArray();
            data.push({"name": "userAction", "value": process});
            claim.serverCall(process, data);

        }
    },
    serverCall: function (action, params) {
//        params.push({"name": "userAction", "value": action});
        $.blockUI();
        try {
            $.ajax({
                url: mawa.url + "/ClaimController",
                type: "POST",
                dataType: 'jsonp',
                crossDomain: true,
                data: params,
                async: false,
                timeout: 10000
            }).done(function (dataR, textStatus, jqXHR) {
                switch (action) {
                    case "create" :
                        claim.view(dataR.value.data);
                        mawa.generatePDF(dataR.value.data);
                        break;
                    case "save":
                        break;
                    case "search":
                        claim.populateResults(dataR.value.data);
                        break;
                    case "get":
                        claim.populateCustomerView(dataR.value.data);
                        break;
                    case "getDependents":
                        $.each(dataR.value.data.dependents, function (index, row) {
                            var fullname = row.id + " " + row.lastName + " " + row.firstName + " " + row.middleName;
                            fullname = fullname.replace("undefined", "");
                            $("#deceased").append('<option value="' + row.id + '">' + fullname + '</option>');
                        });
                        $('.selectpicker').selectpicker('refresh');

                        break;

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
    mawa.getFieldOptions();

    $("#claimForm").validate();
    switch (sessionStorage.action) {
        case "create":
            $("#policyNo").val(sessionStorage.currentTransaction);
            $("#customer").val(sessionStorage.customerFullName);
            claim.getDependents();
            break;
        case "view":
            var data = [];
            data.push({"name": "userAction", "value": "get"});
            data.push({"name": "policyNo", "value": sessionStorage.currentTransaction});
            sessionStorage.action = "get";
            claim.serverCall(data);
            break;
    }

//        $("input[name='payOutMethod']").click(function () {
    $("#claimType").change(function () {
        if ($(this).val() === 'CASH' || $("#groceryClaim").val() === "CASH") {
            $("#cashPayoutBlock").show();
            $("#payoutMethod").rules("add", {
                required: true
            });
        } else {
            // $("#payoutMethod").rules("remove");
            $("#cashPayoutBlock").hide();
        }


        if ($(this).val() === 'FUNERAL') {
            $("#groceryClaimBlock").show();
            $("#groceryClaim").rules("add", {
                required: true
            });
            $("#serviceAreaBlock").show();
            $("#serviceArea").rules("add", {
                required: true
            });

            $("#serviceAreaDetailBlock").show();
            $("#serviceAreaDetail").rules("add", {
                required: true
            });
        } else {
            $("#groceryClaimBlock").hide();
            $("#groceryClaim").rules("remove");
            $("#serviceAreaBlock").hide();
            $("#serviceArea").rules("remove");
            $("#serviceAreaDetailBlock").hide();
            $("#serviceAreaDetail").rules("remove");
        }
    });

//        $("#claimBy").change(function () {
    $("input[name='claimBy']").click(function () {
        if ($(this).val() === 'OTHER') {
            $("#claimantDetailBlock").show();
            $("#claimantDetailBlock :input").rules("add", {
                required: true
            });
            $("#claimantIdNumber").rules("add", {
                validateID: true
            });

        } else {
            // $("#claimantDetailBlock :input").rules("remove");
            $("#claimantDetailBlock").hide();

        }
    });

    $("input[name='groceryClaim']").click(function () {
        if ($(this).val() === "CASH" || $("#claimType").val() === "CASH") {
            $("#cashPayoutBlock").show();
            $("#payoutMethod").rules("add", {
                required: true
            });
        } else {
            $("#cashPayoutBlock").hide();
            $("#payoutMethod").rules("remove");
        }
    });

    $("#payoutMethod").change(function () {
        if ($(this).val() === 'EFT') {
            $("#bankingDetails").show();
            $("#collectionOfficeBlock").hide();
            $("#collectionOfficeBlock :select").rules("remove");
            $("#bankingDetails :input").rules("add", {
                required: true
            });
            $("#accountHolderIdNumber").rules("add", {
                validateID: true
            });
        } else {
            $("#bankingDetails").hide();
            $("#collectionOfficeBlock").show();
            $("#bankingDetails :input").rules("remove");
            $("#collectionOffice").rules("add", {
                required: true
            });
        }
    });

    $("#productCode").change(function () {
        var amount = mawa.getProductPrice($("#productCode").val());
        $("#paymentAmount").val(amount);

    });
//
//    $('#rootwizard').bootstrapWizard({onTabShow: function (tab, navigation, index) {
//            var $total = navigation.find('li').length;
//            var $current = index + 1;
//            var $percent = ($current / $total) * 100;
//            $('#rootwizard').find('.bar').css({width: $percent + '%'});
//        }});
//    $('#rootwizard .finish').click(function () {
//        alert('Finished!, Starting over!');
//        $('#rootwizard').find("a[href*='tab1']").trigger('click');
//    });


    $(".next-step").click(function (e) {

        var $active = $('.wizard .nav-tabs li.active');
        $active.next().removeClass('disabled');
        nextTab($active);

    });
    $(".prev-step").click(function (e) {
        $('.nav-tabs > li a[title]').tooltip();

        //Wizard
        $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {

            var $target = $(e.target);

            if ($target.parent().hasClass('disabled')) {
                return false;
            }
            var $active = $('.wizard .nav-tabs li.active');
            prevTab($active);

        });
    });


});


//$(document).ready(function () {
//    //Initialize tooltips
//
//});



function nextTab(elem) {
    $(elem).next().find('a[data-toggle="tab"]').click();
}
function prevTab(elem) {
    $(elem).prev().find('a[data-toggle="tab"]').click();
}