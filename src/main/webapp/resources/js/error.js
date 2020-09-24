function ErrorPage(e, t, n) {
    this.$container = $(e), this.$contentContainer = this.$container.find(n == "sign" ? ".sign-container" : ".content-container"), this.pageType = t, this.templateName = n
}
ErrorPage.prototype.centerContent = function() {
    var e = this.$container.outerHeight(),
        t = this.$contentContainer.outerHeight(),
        n = (e - t) / 2,
        r = this.templateName == "sign" ? -100 : 0;
    this.$contentContainer.css("top", n + r)
}, ErrorPage.prototype.initialize = function() {
    var e = this;
    this.centerContent(), this.$container.on("resize", function(t) {
        t.preventDefault(), t.stopPropagation(), e.centerContent()
    }), this.templateName == "plain" && window.setTimeout(function() {
        e.$contentContainer.addClass("in")
    }, 500), this.templateName == "sign" && $(".sign-container").animate({
        textIndent: 0
    }, {
        step: function(e) {
            $(this).css({
                transform: "rotate(" + e + "deg)",
                "transform-origin": "top center"
            })
        },
        duration: 1e3,
        easing: "easeOutBounce"
    })
};
var ep = new ErrorPage('body', "404", "sign");
ep.initialize();

// hack to make sure content stays centered >_<
$(window).on('resize', function() {
    $('body').trigger('resize')
});
