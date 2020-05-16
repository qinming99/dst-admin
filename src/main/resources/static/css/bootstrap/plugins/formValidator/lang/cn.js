/**
 * jQuery Form Validator
 * ------------------------------------------
 *
 * Spanish language package
 *
 * @website http://formvalidator.net/
 * @license Dual licensed under the MIT or GPL Version 2 licenses
 * @version 2.2.83
 */
(function($, window) {

  'use strict';

  $(window).bind('validatorsLoaded', function() {

    $.formUtils.LANG = {
      errorTitle: 'Form submission failed!',
      requiredField: '该字段不能为空',
      requiredFields: '该字段不能为空',
      badTime: '时间格式错误',
      badEmail: '邮箱地址格式错误',
      badTelephone: 'You have not given a correct phone number',
      badSecurityAnswer: 'You have not given a correct answer to the security question',
      badDate: '日期格式不正确',
      lengthBadStart: '数值长度必须在 ',
      lengthBadEnd: ' 字符',
      lengthTooLongStart: '该字段长度不能超过 ',
      lengthTooShortStart: '该字段长度至少 ',
      notConfirmed: 'Input values could not be confirmed',
      badDomain: 'Incorrect domain value',
      badUrl: 'The input value is not a correct URL',
      badCustomVal: '数据格式不正确',
      andSpaces: ' and spaces ',
      badInt: '只能填写数字',
      badSecurityNumber: 'Your social security number was incorrect',
      badUKVatAnswer: 'Incorrect UK VAT Number',
      badStrength: 'The password isn\'t strong enough',
      badNumberOfSelectedOptionsStart: 'You have to choose at least ',
      badNumberOfSelectedOptionsEnd: ' answers',
      badAlphaNumeric: '输入的数据仅包含字母或者数字',
      badAlphaNumericExtra: ' 和 ',
      badLetterNumeric: '只能包含字母，数字，中文字符',
      badLetterNumericExtra: ' 和 ',
      wrongFileSize: 'The file you are trying to upload is too large (max %s)',
      wrongFileType: 'Only files of type %s is allowed',
      groupCheckedRangeStart: 'Please choose between ',
      groupCheckedTooFewStart: 'Please choose at least ',
      groupCheckedTooManyStart: 'Please choose a maximum of ',
      groupCheckedEnd: ' item(s)',
      badCreditCard: 'The credit card number is not correct',
      badCVV: 'The CVV number was not correct',
      wrongFileDim : 'Incorrect image dimensions,',
      imageTooTall : 'the image can not be taller than',
      imageTooWide : 'the image can not be wider than',
      imageTooSmall : 'the image was too small',
      min : 'min',
      max : 'max',
      imageRatioNotAccepted : 'Image ratio is not be accepted',
      badBrazilTelephoneAnswer: 'The phone number entered is invalid',
      badBrazilCEPAnswer: 'The CEP entered is invalid',
      badBrazilCPFAnswer: 'The CPF entered is invalid'
    };

  });

})(jQuery, window);
