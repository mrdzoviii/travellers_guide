function disable(){
	PF('ad_upload').disableButton(PF('ad_upload').chooseButton);
    PF('ad_upload').chooseButton.find('input[type="file"]').attr('disabled', 'disabled');
}