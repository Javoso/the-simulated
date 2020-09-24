(function($) {
	$(document).ready(function() {
		var url = window.location;

		var page = window.location.pathname;

		 if(page === "/Simulado/") {
		 page += "index.xhtml";
		 }
					
//		 $('ul.sidebar-menu a[href="' + page + '"]')
//		 .parent().addClass('active');
		
		$('ul.sidebar-menu a').filter(function() {
			return this.href == url;
		}).parent().parent().parent().addClass('active');
	});
})(jQuery);
