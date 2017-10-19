var TRANSFER_URL = 'http://localhost:8080/api/v1/transfers';
var app = angular.module("Rf", [ 'ngResource' ]);

app.factory('transferService', [ '$resource', function($resource) {
	console.log("transferService TRANSFER_URL: " + TRANSFER_URL);
	var Transfers = $resource(TRANSFER_URL, {}, {
		query : {
			method : "GET",
			isArray : true
		}
	});
	return function() {
		// does the external call
		return Transfers.query();
	};
} ]);


app.factory('sendTransferService', [ '$resource', function($resource) {
	console.log("sendTransferService TRANSFER_URL: " + TRANSFER_URL);
	var Transfer = $resource(TRANSFER_URL, {}, {
		transfer : {
			method : "POST",
			params : {},
			headers : {
				'Content-Type' : 'application/json'
			}
		}
	}
	);
	return function(entry) {
		return Transfer.transfer(entry);
	};
} ]);


app.controller('SendTransferCtrl', [ '$scope', 'sendTransferService', 'transferService', function($scope, sendTransferService, transferService) {
	$scope.transfersCall = function() {
		console.log("recovering transfers :)");
		$scope.transfers = transferService();
		$scope.transfers.$promise.then(function(result) {
			console.log('result ' + result);
			console.log($scope.transfers.length);
		}, function(error) {});
	}
	$scope.sendTransfer = function() {
		showSuccessMessage(null);
		delete $scope.errors;
		var content = new Object();
		content.destinationAccount = $('#destinationAccount').val();
		content.originAccount = $('#originAccount').val();
		content.scheduleDate = $('#scheduleDate').val();
		content.transferValue = $('#transferValue').val();
		console.log("content " + content);
		$scope.sentTransfer = sendTransferService(JSON.stringify(content));
		$scope.sentTransfer.$promise.then(function(result) {
			console.log(result);
			clearForm();
			showSuccessMessage('Transfer ' + result.id + ' successfully scheduled to ' + result.scheduleDate);
			//update transfers -- start
			$scope.transfers = transferService();
			$scope.transfers.$promise.then(function(result) {
				console.log('result ' + result);
				console.log($scope.transfers.length);
			}, function(error) {});
		//update transfers -- end
		}, function(error) {
			console.log('setting error: ' + error.data);
			$scope.errors = error.data;
		});
	}
} ]);

function clearForm() {
	$('#destinationAccount').val('');
	$('#originAccount').val('');
	$('#scheduleDate').val('');
	$('#transferValue').val('');
}
function showSuccessMessage(message) {
	if (message != null) {
		console.log("showing ");
		$('#message').html(message);
		$('#message').show('slow');
	} else {
		console.log("hidding");
		$('#message').html('');
		$('#message').hide('slow');
	}
}

function showItem(id) {
	if ($('#trans' + id).is(':visible')) {
		$('#trans' + id).hide('slow');
	} else {
		$('#trans' + id).show('slow');
	}
}