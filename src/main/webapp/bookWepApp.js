$(function(){
    $("#author").change(function(){
        var author = $('#author').val();
        
        if(author == "insert"){
            $('#insertModal').modal('show');
            console.log(author);
        }else{
            console.log(author);
        }
        
    });
    
    $('#addAuthor').click(function(){
        var isValid = $('#insertForm').validate().form();
    });
    
    $('#submitUpdate').click(function(){
        var isValid = $('#insertForm').validate().form();
    });
    
    $('#insertForm').validate({
        rules: {
            addName: {
                required: true,
                minlength: 2
            },
            addDate:{
                date: true
            }
        }
    });
    
    $('#updateForm').validate({
        rules: {
            updateName: {
                minlength: 2
            },
            updateDate:{
                date: true
            }
        }
    });
});

