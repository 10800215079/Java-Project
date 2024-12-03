/**
 * 
 */
    
    //OTP modal close and cancle
    
	function closeandCancelModal() {
		var checkbox = $("#agree1");
				$("#otp").hide();
				$("#resentOTP").hide();
				$("#OTPvalidatebtn").hide();
				$("#sendOTP1").show();
				$(".left10minutes").hide();
				$(".opt2minutes").hide();
				$("#agree1").prop("disabled", false);
				if (checkbox.length > 0) {
					if (checkbox.is(":checked")) {
						checkbox.prop("checked", false);
						 $('#sendOTP1').prop('disabled', true);
					}
				}
				$('#otpPopupP1').modal('hide');
	}


    // OTP success modal close and cancle
    
	function closeSuccesModal() {
		$('#optverifedsuccessfully').modal('hide');
		location.reload();
	}


  // checkbox agree button
	function concernCheckbox(element) {
	
		if ($(element).is(':checked')) {
			$('#sendOTP1').prop('disabled', false);
		}
		else {
			$('#sendOTP1').prop('disabled', true);
		}
	}
		        
		        
			
	// Send OTP		
	function sendOtp() {
		event.preventDefault();
		generateOTP();
		$("#otp").show();
		$("#resentOTP").show();
		$("#OTPvalidatebtn").show();
		$("#sendOTP1").hide();
		$(".left10minutes").show();
		$(".opt2minutes").show();
		$("#agree1").prop("disabled", true);
		startOTPTimer();
	
	}
	
	
	//Resend OTP
	function resendOtp() {
		event.preventDefault();
		generateOTP();
	}


	var otpTimer;
	var timerDuration = 2 * 60; // 2 minutes
	
	function startOTPTimer() {
		var timerDisplay = $('.otp-countdown');
		var remainingTime = timerDuration;
		otpTimer = setInterval(function() {
			var minutes = Math.floor(remainingTime / 60);
			var seconds = remainingTime % 60;
			var display = (minutes < 10 ? '0' : '') + minutes + ':' + (seconds < 10 ? '0' : '') + seconds;
			timerDisplay.text(display);
			remainingTime--;
	
			if (remainingTime < 0) {
				clearInterval(otpTimer);
				$("#regenerateOTP").show();
				$("#otpFields, #submitOTP").hide();
			}
		}, 1000);
	}
					
	function generateOTP() {
		debugger;
		var uuid = $("#aadhaar").val();
		$.ajax({
			type: 'GET',
			url: '/getOTP?UUID=' + uuid,
			success: function(response) {
				debugger;
				if (response.status == 'Y' || response.status == 'y') {
					alert('OTP Sent Successfully');
					var newOTP = response.otp;
					$("#otp").val(newOTP);
					startOTPTimer();
				} else {
					alert('Unable to Generate OTP');
				}
	
			},
			error: function(xhr, status, error) {
				console.log('Error: ' + error);
			}
		});
	
	}
	
	
						
					
					

