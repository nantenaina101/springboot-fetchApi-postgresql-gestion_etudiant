let profilPicture = document.querySelector("#profilPicture");
let fileInputProfil = document.querySelector("#fileInputProfil");
let couverturePicture = document.querySelector("#couverturePicture");
let fileInputCouverture = document.querySelector("#fileInputCouverture");
let avatar = "";
let avatarCouverture = "";
fileInputProfil.addEventListener("change", (e) => {

        ///read file
        const fileReader = new FileReader();

        ////on load file
        fileReader.addEventListener("load", () => {

            avatar = fileReader.result;
			
            console.log(avatar);

            // Change profil
            profilPicture.src = avatar
            
            var form = new FormData()
			form.append('file', fileInputProfil.files[0])
		

            fetch("/api/v1/update-profil-picture", {
                method: "PUT",
                body: form
            }).then(x => x.text()).then(response => console.log(response));

        });

        ///run event load in file reader.
        fileReader.readAsDataURL(e.target.files[0]);

})

fileInputCouverture.addEventListener("change", (e) => {

        ///read file
        const fileReader = new FileReader();

        ////on load file
        fileReader.addEventListener("load", () => {

            avatarCouverture = fileReader.result;
			
            console.log(avatarCouverture);

            // Change Couverture
            couverturePicture.src = avatarCouverture
            
            var form = new FormData()
			form.append('file', fileInputCouverture.files[0])
		

            fetch("/api/v1/update-couverture-picture", {
                method: "PUT",
                body: form
            }).then(x => x.text()).then(response => console.log(response));

        });

        ///run event load in file reader.
        fileReader.readAsDataURL(e.target.files[0]);

})