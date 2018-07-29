function handleLoginRequest(xhr, status, args) {
			if (args.validationFailed || !args.loggedIn) {
				PF('loginVar').jq.effect("shake", {
					times : 5
				}, 100);
			} else {
				PF('loginVar').hide();
			}
}
function showLogin(varName){
	PF(varName).show();
}