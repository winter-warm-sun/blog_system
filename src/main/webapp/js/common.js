//这个文件放一些页面公共的代码

function getUserInfo(pageName) {
    $.ajax({
        type:'get',
        url:'login',
        success:function(body) {
            //判定此处的body是不是一个有效的user对象(userId是否为0)
            if(body.userId&&body.userId>0) {
                //登录成功，不做处理
                console.log("当前用户登录成功! 用户名: " + body.username);

                if(pageName=='blog_list.html') {
                    changeUserName(body.username);
                }
            }else {
                //登录失败！
                //让前端页面跳转到login.html
                alert("当前您尚未登录！请登录后再访问博客列表！");
                location.assign('blog_login.html');
            }
        },
        error: function() {
            alert("当前您尚未登录! 请登录后再访问博客列表!");
            location.assign('blog_login.html');
        }
    });
}

function changeUserName(username) {
    let h3=document.querySelector('.card>h3');
    h3.innerHTML=username;
}