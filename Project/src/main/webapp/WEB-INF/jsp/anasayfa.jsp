<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="tr-TR">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Document</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-12" style="margin-left: 13%;margin-top: 20%;" >
            <form action="aramasonuc" method="post" class="form-inline">
                    <div class="form-group" style="margin-right: 5px;">
                        <input type="text" class="form-control" id="usr" name="arama" placeholder="Bir seyler arayin..." style="width: 400px;">
                    </div>

                    <div class="form-group" style="margin-right: 5px;">
                        <select class="form-control" id="sel1" name="kacyuz" required style="width: 180px;">
                            <option value="">Kac adet getirilsin</option>
                            <option value="1">100</option>
                            <option value="2">200</option>
                            <option value="3">300</option>
                            <option value="4">400</option>
                            <option value="5">500</option>
                            <option value="10">1000</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" style="width: 80px;">Ara</button>

            </form>
        </div>
    </div>
</div>
</body>
</html>