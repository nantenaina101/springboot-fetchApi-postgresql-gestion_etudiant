const searchInput = document.querySelector("#search")
let container = document.querySelector("#container")
let studentArray;

let image = document.querySelector("#image");
let contentImage = document.querySelector("#contentImage");
let imgTemp = "";

image.addEventListener("change", (e) => {

    ///read file
    const fileReader = new FileReader();

    ////on load file
    fileReader.addEventListener("load", () => {

    imgTemp = fileReader.result;

    console.log(imgTemp);

    // Afficher la photo
    contentImage.style.display = "block";
    contentImage.src = imgTemp
    contentImage.style.marginBottom = "10px";

    });
		

    ///run event load in file reader.
    fileReader.readAsDataURL(e.target.files[0]);

})

function getStudents(){
	fetch("/api/v1/etudiants")
	.then(response=>{	
		return response.json()
	}).then(results => {
		studentArray = orderList(results)
		createStudentList(studentArray)
	})
}


getStudents()

function orderList(data) {

  const orderedData = data.sort((a,b) => {
    if(a.firstname.toLowerCase() < b.firstname.toLowerCase()) {
      return -1;
    }
    if(a.firstname.toLowerCase() > b.firstname.toLowerCase()) {
      return 1;
    }
    return 0;
  })
  
  return orderedData;
}

function createStudentList(studentList) {
	
	if(studentList.length > 0){
		
		let table = document.createElement("table");
		
		table.classList.add("table");
		table.classList.add("table-bordered");
		//table.classList.add("text-center");
		table.id = "table"
		
		let tableIner = `<thead>
			                <tr>
			                	<th>Profil</th>
			                    <th>Prénom</th>
			                    <th>Nom</th>
			                    <th>Email</th>
			                    <th>Téléphone</th>
			                    <th>Sexe</th>
			                    <th>Classe</th>
			                    <th>Actions</th>
			                </tr>
			            </thead>`
		            
		table.innerHTML = tableIner
		let tbody = document.createElement("tbody");
		let tr = ""
		container.innerHTML = ""
		
		studentList.forEach(data => {
			
		let classe = ""
			switch (data.classe) {
			  case 1:
			    classe = "6è";
			    break;
			  case 2:
			    classe = "5è";
			    break;
			  case 3:
			     classe = "4è";
			    break;
			  case 4:
			    classe = "3è";
			    break;
			  case 5:
			    classe = "Seconde";
			    break;
			  case 6:
			    classe = "Première";
			    break;
			  case 7:
			    classe = "Terminale";
			    break;
  			  default:
  			  	classe = "6è";
			}
			
			let image = "";
			
			if(data.image != null){
				image = `<img id="contentImage" style="min-width:40px; min-height: 40px;max-width:40px; max-height: 40px;" src="/uploads/${data.image}">`;
			}else{
				image = "Aucune";
			}
			
		    tr += `<tr>
		    			<td class="image text-center">
		    				${image}
		    			</td>
		                <td class="firstname">${data.firstname}</td>
		                <td class="lastname">${data.lastname}</td>
		                <td class="email">${data.email}</td>
		                <td class="telephone">${data.telephone}</td>
		                <td class="sexe">${data.sexe}</td>
		                <td class="classe" data-id="${data.classe}">${classe}</td>
		                <td><a href="#mainTitle" type="button" class="btn" onclick="openEditForm(this, ${data.id})">
		                		<i style="color:blue;" class="bi bi-pencil-square"></i>
		                	</a>
		                	<span>&nbsp;&nbsp;</span>
		                	<a type="button" class="btn" data-toggle="modal" onclick="openModal(${data.id}, '${data.firstname} ${data.lastname}')" data-target="#deleteStudentModal">
		                		<i style="color:red;" class="bi bi-trash3"></i>
		                	</a>
		                </td>		
		            </tr>`
	  })
	  
	  tbody.innerHTML = tr
	  
	  table.appendChild(tbody);
	  
	  container.appendChild(table);
	}else{
		container.innerHTML = ""
		let h5 = document.createElement("h5");
		h5.innerHTML = "Aucun étudiant"
		container.appendChild(h5);
	}
	
}

searchInput.addEventListener("input", filterData)

function filterData(e) {

  const searchedString = e.target.value.toLowerCase().replace(/\s/g, "");

  const filteredArr = studentArray.filter(el => 
    el.firstname.toLowerCase().includes(searchedString) ||
    el.email.toLowerCase().includes(searchedString) || 
    el.lastname.toLowerCase().includes(searchedString) ||
    `${el.firstname + el.lastname}`.toLowerCase().replace(/\s/g, "").includes(searchedString) ||
    `${el.lastname + el.firstname}`.toLowerCase().replace(/\s/g, "").includes(searchedString)
    )

  createStudentList(filteredArr)
}

function openModal(id,fullname){
	document.querySelector("#removeStudent").dataset.id = id;
	document.querySelector("#etudiantName").textContent = fullname;
}

function removeStudent(element){
	fetch("/api/v1/etudiant/"+element.dataset.id, {
        method: 'DELETE',
        headers: {
            'Content-type': 'text/html'
        }
   })
	.then(response=>{	
		return response.text()
	}).then(texte => {
		//console.log(texte);
		document.querySelector("#successMessageBlock").style.display = "block"
		document.querySelector("#successMessage").textContent = texte
		getStudents()
		setTimeout(() => {	
			document.querySelector("#successMessageBlock").style.display = "none"
			}, 3000);
	})
}

document.querySelector("#addBtn").addEventListener("click", ()=>{
	
	document.querySelector("#classe").value = 1
	document.querySelector("#masculin").checked = true
	document.querySelector("#firstname").value = ""
	document.querySelector("#lastname").value = ""
	document.querySelector("#email").value = ""
	document.querySelector("#telephone").value = ""
	contentImage.style.display = "none";
    	
    contentImage.style.marginBottom = "0px";
	
	if(document.querySelector("#form").style.display == "none"){
		document.querySelector("#form").style.display = "block"
		document.querySelector("#submitForm").value = "Enregistrer"
		document.querySelector("#addBtn").innerHTML = `<i style="color:red;" class="bi bi-x-circle"></i>&nbsp;&nbsp;Fermer le formulaire`
	}else{
		document.querySelector("#form").style.display = "none"
		document.querySelector("#methode").value = "add_student"
		document.querySelector("#addBtn").innerHTML = `<i style="font-size:20px;" class="bi bi-person-fill-add"></i>&nbsp;&nbsp;Ajouter un étudiant`;
	}
})

if(document.querySelector("#form")){
	document.querySelector("#form").addEventListener("submit", (e)=>{
		
		e.preventDefault()
		
		let classe = document.querySelector("#classe").value
		let sexe = "masculin"
		if(document.querySelector("#feminin").checked){
			sexe = "feminin"
		}else{
			sexe = "masculin"
		}
		let firstname = document.querySelector("#firstname").value
		let lastname = document.querySelector("#lastname").value
		let email = document.querySelector("#email").value
		let telephone = document.querySelector("#telephone").value
		
		let data = {
        "classe": classe,
        "sexe": sexe,
       	"firstname": firstname,
        "lastname": lastname,
        "email": email,
        "telephone": telephone,
        //"image":image.files[0],
        //"image":imgTemp
    	}
    	
    	console.log(data)

		var form = new FormData()
		form.append('file', image.files[0])
		form.append('user', 'hubot')
    	
    	if(document.querySelector("#methode").value == "add_student"){
	    	fetch('/api/v1/add-etudiant', {
			  method: 'POST',
			  body: JSON.stringify(data),
			  //body: form,
			  headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
			  })
			  .then(response=>{	
					return response.text()
				}).then(texte => {
					console.log(texte);
					
					document.querySelector("#classe").value = 1
					document.querySelector("#masculin").checked = true
					document.querySelector("#firstname").value = ""
					document.querySelector("#lastname").value = ""
					document.querySelector("#email").value = ""
					document.querySelector("#telephone").value = ""
					
					contentImage.style.display = "none";
    	
    				contentImage.style.marginBottom = "0px";
    				
    				//document.querySelector("#form").style.display = "none"
					
					getStudents()
									
				})
			
			}else{
				//console.log("value" + e.target.value)
				fetch('/api/v1/update-etudiant/'+e.target.value, {
				  method: 'PUT',
				  body: JSON.stringify(data),
				  headers: {
				    'Content-type': 'application/json; charset=UTF-8',
				  }
				  })
				  .then(response=>{	
						return response.text()
					}).then(texte => {
						console.log(texte);
						
						document.querySelector("#successMessageBlock").style.display = "block"
						document.querySelector("#successMessage").textContent = texte
						getStudents()
						setTimeout(() => {	
							document.querySelector("#successMessageBlock").style.display = "none"
							}, 3000);
						
						document.querySelector("#classe").value = 1
						document.querySelector("#masculin").checked = true
						document.querySelector("#firstname").value = ""
						document.querySelector("#lastname").value = ""
						document.querySelector("#email").value = ""
						document.querySelector("#telephone").value = ""
						
						contentImage.style.display = "none";
    	
    					contentImage.style.marginBottom = "0px";
						
						document.querySelector("#form").style.display = "none"
						document.querySelector("#methode").value = "add_student"
						document.querySelector("#addBtn").innerHTML = `<i style="font-size:20px;" class="bi bi-person-fill-add"></i>&nbsp;&nbsp;Ajouter un étudiant`;
						
						getStudents()
										
					})
			}
		
	})	
}

function openEditForm(element,id){
	document.querySelector("#form").style.display = "block"
	document.querySelector("#form").value = id
	document.querySelector("#addBtn").innerHTML = `<i style="color:red;" class="bi bi-x-circle"></i>&nbsp;&nbsp;Fermer le formulaire`
	document.querySelector("#submitForm").value = "Modifier"
	document.querySelector("#methode").value = "edit_student"
	//console.log(element.parentElement.parentElement)
	document.querySelector("#classe").value = element.parentElement.parentElement.querySelector(".classe").dataset.id
	document.querySelector("#"+element.parentElement.parentElement.querySelector(".sexe").textContent).checked = true
	document.querySelector("#firstname").value = element.parentElement.parentElement.querySelector("td.firstname").textContent
	document.querySelector("#lastname").value = element.parentElement.parentElement.querySelector("td.lastname").textContent
	document.querySelector("#email").value = element.parentElement.parentElement.querySelector("td.email").textContent
	document.querySelector("#telephone").value = element.parentElement.parentElement.querySelector("td.telephone").textContent
	contentImage.style.display = "block";
    contentImage.src = element.parentElement.parentElement.querySelector("td.image > img").src
    contentImage.style.marginBottom = "10px";
}


