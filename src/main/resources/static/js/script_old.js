const searchInput = document.querySelector("#search")
let container = document.querySelector("#container")
let studentArray;


function getStudents(){
	fetch("http://localhost:8080/api/v1/etudiants")
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
			
		    tr += `<tr>
		                <td>${data.firstname}</td>
		                <td>${data.lastname}</td>
		                <td>${data.email}</td>
		                <td>${data.telephone}</td>
		                <td>${data.sexe}</td>
		                <td>${classe}</td>
		                <td><a href="/edit/${data.id}">
		                		<i style="color:blue;" class="bi bi-pencil-square"></i>
		                	</a>
		                	<span>&nbsp;&nbsp;</span>
		                	<a href="/delete/${data.id}">
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

/*
fetch("http://localhost:8080/api/v1/etudiants")
.then(response=>{	
	return response.json()
}).then(datas => {
	
	//console.log(datas.length)
	
	let container = document.querySelector("#container")
	
	let content = "<h5>Aucun étudiant enregistré</h5>"
	
	if(datas.length > 0){
		content = `<table class="table table-bordered text-center" id="table"><thead>
		                <tr>
		                    <th>ID</th>
		                    <th>Classe</th>
		                    <th>Sexe</th>
		                    <th>Prénom</th>
		                    <th>Nom</th>
		                    <th>Email</th>
		                    <th>Téléphone</th>
		                    <th>Actions</th>
		                </tr>
		            </thead>
		            <tbody>`
		          
		 for(let data of datas){
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
			content += `<tr>
			            	<td>${data.id}</td>
			            	<td>${classe}</td>
			            	<td>${data.sexe}</td>
			                <td>${data.firstname}</td>
			                <td>${data.lastname}</td>
			                <td>${data.email}</td>
			                <td>${data.telephone}</td>
			                <td><a href="/edit/${data.id}">
			                		<i style="color:blue;" class="bi bi-pencil-square"></i>
			                	</a>
			                	<span>&nbsp;&nbsp;</span>
			                	<a href="/delete/${data.id}">
			                		<i style="color:red;" class="bi bi-trash3"></i>
			                	</a>
			                </td>		
			            </tr>`
		}
		            
		content += `</tbody></table>`
		 
	}
	console.log(content)
	container.innerHTML = content
	
	})*/