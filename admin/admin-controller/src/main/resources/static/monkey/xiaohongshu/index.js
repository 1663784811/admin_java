(function () {

    loadData();

})()


function loadData() {
    var container = $(".feeds-container .note-item")
    if (container.length == 0) {
        console.log("没有找到数据一秒后得重试")
        setTimeout(loadData, 1000)
    } else {
        console.log("共找到  " + container.length + "  条数据, 开始处理数据")
        delWithData(container)
        for (let i = 0; i < container.length; i++) {
            console.log("正在处理条" + i + "条数据")
            console.log($(".feeds-container .note-item").get(i).txt())
            console.log($(".feeds-container .note-item").get(i))


        }
    }
}

function delWithData(container) {




}