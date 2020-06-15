$(function (){
  $('.btn-register').on('click', function (event){
    event.preventDefault();
    console.log("register");
    var postBody = $('#registerForm').serialize();
    console.log(postBody);

    $.ajax({
      type: 'post',
      async: false,
      url: 'user/register',
      data: postBody,
    }).done(function (){
      alert('SUCCESS');
      location.href = '../login'
    }).fail(function (xhr, status){
      console.log(xhr);
      alert("ERROR:" + status);
    });

  });


});

function onSignIn(googleUser){
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
  console.log('Token: ' + googleUser.getAuthResponse().id_token);
  $('#email').val(profile.getEmail());
  $('#fullName').val(profile.getName());
  $('#accessToken').val(googleUser.getAuthResponse().id_token);
  $('.g-signin2').hide();
}
