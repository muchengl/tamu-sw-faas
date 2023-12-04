var items = document.getElementsByClassName('item');//图片
var goPreBtn = document.getElementById('goPre');
var goNextBtn = document.getElementById('goNext');
var points = document.getElementsByClassName('point');//点

var index = 0;//表示第几张图片有active//第几个点

var time=0;//停留时间

var clearActive = function () {
    for (var i = 0; i < items.length; i++) {
        items[i].className = 'item';
    }
    for (var i = 0; i < items.length; i++) {
        points[i].className = 'point';
    }
}

var goIndex = function () {
    clearActive();
    points[index].className = 'point active';
    items[index].className = 'item active';
}

var goNext = function () {
    if (index < 3) {
        index++
    } else {
        index = 0;
    }
    goIndex();
}

var goPre = function () {
    if (index == 0) {
        index = 3
    } else {
        index--;
    }
    goIndex();
}

goPreBtn.addEventListener('click', function () {
    goPre();
})

goNextBtn.addEventListener('click', function () {
    goNext();
})

for (var i = 0; i < points.length; i++) {
    points[i].addEventListener('click', function () {
        var pointIndex = this.getAttribute('data-index');
        index = pointIndex;
        goIndex();
        time=0;
    })
}



setInterval(function () {
    time++;
    if(time==20){
        goNext();
        time=0;
    }
}, 100)