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
	alert("We're very sorry, but you can not install this project on an iOS application.")
  }