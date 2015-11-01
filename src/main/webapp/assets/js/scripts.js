
jQuery(document).ready(function() {
	
    /*
        Fullscreen background
    */

	    $.backstretch([
                    "../assets/img/backgrounds/2.jpg"
                , "../assets/img/backgrounds/3.jpg"
                , "../assets/img/backgrounds/1.jpg"
               ], {duration: 3000, fade: 750});
    
    /*
	    Modals
	*/
	$('.launch-modal').on('click', function(e){
		e.preventDefault();
		$( '#' + $(this).data('modal-id') ).modal();
	});
    
    /*
        Form validation
    */
    $('.registration-form input[type="text"], .registration-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    $('.registration-form').on('submit', function(e) {
    	
    	$(this).find('input[type="text"], textarea').each(function(){
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		}
    		else {
    			$(this).removeClass('input-error');
    		}
    	});
    	
    });
	
	$("#doctorLogin").on('click',function(){
	 window.location.href = 'index.html';
    return false;
	});
});
