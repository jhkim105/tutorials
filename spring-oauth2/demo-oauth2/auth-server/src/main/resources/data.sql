INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('client01', '{noop}secret01', 'foo,read,write',
	'password,authorization_code,refresh_token,client_credentials,implicit', 'http://localhost:8080/authorization-code', null, 36000, 36000, null, true);
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('client02', '{noop}secret02', 'read,write,foo,bar',
	'password', null, null, 36000, 36000, null, false);
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('client03', '{noop}secret03', 'bar,read,write',
	'client_credentials', null, null, 36000, 36000, null, true);