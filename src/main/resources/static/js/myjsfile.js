$(function() {
    $( "#slider-range-max" ).slider({
        range: "max",
        min: 1,
        max: 1000,
        value: 2,
        slide: function( event, ui ) {
            $( "#amount" ).val( ui.value );
        }
    });
    $( "#amount" ).val( $( "#slider-range-max" ).slider( "value" ) );
});

//var time = new Date();
//alert("timeget Month = "+time.getMonth())
/*动态文字时钟*/
var click = setInterval(function() {
    var time = new Date();
    var timeStr = time.getFullYear()+'年'+time.getMonth()+'月'+time.getDate()+'日 '+
        time.getHours()+':'+time.getMinutes()+':'+time.getSeconds();
    $("#click").html(timeStr).css();
}, 1000);