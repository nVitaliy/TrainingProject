var app = angular.module("myApp", ["ngRoute"]);
app.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when("/headers", {
            templateUrl: "/views/headers.html"
        })
        .when("/readingBooks", {
            templateUrl: "/views/readingBook.html"
        })
        .when("/greeting", {
            templateUrl: "/views/greeting.html"
        })
        .otherwise('/');
    // $locationProvider.html5Mode({
    //     enabled: true,
    //     requireBase: false
    // });
});
