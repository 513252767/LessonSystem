
    $(function () {
        //登录页面验证码判断
        $("#checkCode1").onmouseover=function () {
            var codeLogin = '${checkCodeLogin}';
            var checkCode = document.getElementById(checkCode1);
            if( codeLogin == checkCode ){
                $("#checkCode1").css("background-color","green");
                $("#buttonLogin").attr("disabled",false);
            }else {
                $("#checkCode1").css("background-color","red")
                $("#buttonLogin").attr("disabled",true);
            }
        }
    })

    $(function () {
        //注册页面验证码判断
        $("#checkCode2").onblur=function () {
            var codeRegister = '${checkCodeRegister}';
            var checkCode = document.getElementById(checkCode1);
            if( codeRegister == checkCode ){
                $("#checkCode2").css("background-color","green");
                $("#buttonRegister").attr("disabled",false);
            }else {
                $("#checkCode2").css("background-color","red");
                $("#buttonRegister").attr("disabled",true);
            }
        }
    })
