$(function (){
  // login
  $('.btn-login').on('click', function (event){
    event.preventDefault();
    console.log("login");
    $('#login').submit();
  });

  // login with Google
  $('.btn-google').on('click', function (event){
    event.preventDefault();
    console.log("google login");
  });

});

function onSignIn(googleUser){
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
  console.log('Token: ' + googleUser.getAuthResponse().id_token);
}