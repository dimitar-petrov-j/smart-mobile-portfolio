//See if the browser supports Service Workers, if so try to register one
if("serviceWorker" in navigator){
    navigator.serviceWorker.register("service-worker.js").then(function(registering){
      // Registration was successful
      console.log("Browser: Service Worker registration is successful with the scope",registering.scope);
    }).catch(function(error){
      //The registration of the service worker failed
      console.log("Browser: Service Worker registration failed with the error",error);
    });
  } else {
    //The registration of the service worker failed
    console.log("Browser: I don't support Service Workers :(");
}

//Asking for permission with the Notification API
if(typeof Notification!==typeof undefined){ //First check if the API is available in the browser
	Notification.requestPermission().then(function(result){ 
		//If accepted, then save subscriberinfo in database
		if(result==="granted"){
			console.log("Browser: User accepted receiving notifications, save as subscriber data!");
			navigator.serviceWorker.ready.then(function(serviceworker){ //When the Service Worker is ready, generate the subscription with our Serice Worker's pushManager and save it to our list
				const VAPIDPublicKey="BO0BNVXEyAVXiK08jLxFPM1M7UfJ2KJfq9KXq92UqoSIkEkRDFDt1z_SGbZ7E6ojGpd3SJpXXJrWxHIveBN4Rpk"; // Fill in your VAPID publicKey here
				const options={applicationServerKey:VAPIDPublicKey,userVisibleOnly:true} //Option userVisibleOnly is neccesary for Chrome
				serviceworker.pushManager.subscribe(options).then((subscription)=>{
          //POST the generated subscription to our saving script (this needs to happen server-side, (client-side) JavaScript can't write files or databases)
					let subscriberFormData=new FormData();
					subscriberFormData.append("json",JSON.stringify(subscription));
					fetch("data/saveSubscription.php",{method:"POST",body:subscriberFormData}).then((success)=>{ console.log(success)}).catch((error)=>{ console.log(error)});
				});
			});
		}
	}).catch((error)=>{
		console.log(error);
	});
}
 

let installPrompt; //Variable to store the install action in
window.addEventListener("beforeinstallprompt",(event)=>{	
	event.preventDefault(); //Prevent the event (this prevents the default bar to show up)
	installPrompt=event; //Install event is stored for triggering it later
	console.log("log")
	document.getElementById("installbutton").style.display = "block";
});


function runInstall(){
//Recognize the install variable from before?
installPrompt.prompt();
document.getElementById("installbutton").style.display = "none";
installPrompt.userChoice.then((choiceResult)=>{
	document.getElementById("installbutton").style.display = "none";
	if(choiceResult.outcome!=="accepted"){
		document.getElementById("installbutton").style.display = "block";
  }
  installPrompt=null;
});
}

//iOS install tip show
const isIOSUsed=()=>{
	const userAgent=window.navigator.userAgent.toLowerCase();
	return /iphone|ipad|ipod/.test(userAgent); //Return if iOS is used (iPhone, iPod or iPad)
  }
  const standaloneModeActive=()=>("standalone" in window.navigator)&&(window.navigator.standalone); //Will be true if the PWA is used
  if(isIOSUsed()&&!standaloneModeActive()){ 
	//Show your install tip for iOS here
  }