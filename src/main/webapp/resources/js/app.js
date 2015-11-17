(function($, window, document){
$(function(){
    var authorBaseUrl = "AuthorController";
    
    var $document = $(document);
    var $authorList = $('#authorList');
    var $body = $('body');
    
    $document.ready(function() {
        console.log("document ready");
        if($body.attr('class') === "authorDisplay"){
            console.log("getting Json...");
            $.ajax({
                type: 'GET',
                url: authorBaseUrl + "?action=ajaxFindAll",
                success: function(authors){
                    displayAuthors(authors);
                },
                error: function(jqXHR, textStatus, error){
                    handleError(error);
                }
            });
        }
    });
    
    function displayAuthors(authors){
        $.each(authors, function(index, author){
            var row = '<tr class="authorListRow">' +
                    '<td>' + author.authorId + '</td>' +
                    '<td>' + author.authorName + '</td>' +
                    '<td>' + author.dateAdded + '</td' +
                    '</tr>';
            $authorList.append(row);
        });
    }
    
    function handleError(error){
        $('#errorAlert').show();
        $('#err').html(error);
    }
    
    $authorList.on('mouseover', 'tr', function(){
        console.log("Mouseover");
        $(this).toggleClass('highlight');
    });
    
    $authorList.on('mouseout', 'tr', function(){
        console.log("mousout");
        $(this).toggleClass('highlight');
    });
});
}(window.jQuery, window, document));