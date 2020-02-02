<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</head>
<body>
<div class="jumbotron">
    <h1 class="display-4">Hello, world!</h1>
    <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
    <hr class="my-4">
    <p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
    <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
</div>
<div class="container">
    <div class="row">
        <div class="form-group">
            <form class="form-controll" action = "ekle" method = "post">
                <div class="row">
                    <div class="col">
                        say1: <input class="form-control" type = "text" name = "say1">
                    </div>
                    <div class="col">
                        say2: <input class="form-control" type = "text" name = "say2">
                    </div>
                    <div class="col">
                        Arama: <input class="form-control" type = "text" name = "arama" />
                    </div>

                    <div class="col">
                        <input class="btn btn-success" type = "submit" value = "Submit" />
                    </div>
                    <div class="form-group">
                        <label for="sel1">Select list:</label>
                        <select class="form-control" name="y" id="sel1">
                            <option value="1">100</option>
                            <option value="2">200</option>
                            <option value="3">300</option>
                            <option value="5">500</option>
                            <option value="10">1000</option>
                        </select>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>

</body>
</html>



