$(document).ready(function(){
    $('#chatbot-link').click(function(event) {
        event.preventDefault();
        if (!$('#chatbot-container').length) {
            $('body').append('<div id="chatbot-container"><span id="chatbot-close">&times;</span><iframe id="chatbot-iframe" allow="microphone;" width="100%" height="100%" src="https://console.dialogflow.com/api-client/demo/embedded/8ab8e57c-9b18-4663-b628-8e0f8649376c"></iframe></div>');

            $('#chatbot-close').click(function() {
                $('#chatbot-container').remove();
            });
        }
    });
});