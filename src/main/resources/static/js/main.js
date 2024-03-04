// $(document).ready(function () {
//
//
//     $('.btnEdit').on('click', function (event) {
//         console.log("btnEdit");
//         event.preventDefault();
//         var href = $(this).attr("href");
//         $.get(href, function (userInfo, status) {
//             let userVar = userInfo[0];
//             let userRole = userInfo[1];
//             // th:readonly="true"
//             $('#editId').val(userVar.id);
//             $('#editName').val(userVar.firstName);
//
//             $('#editLastName').val(userVar.lastName);
//             $('#editAge').val(userVar.age);
//             $('#editEmail').val(userVar.email);
//             if (userRole === '[ADMIN]') {
//                 RadionButtonSelectedValueSet("roles", 1)
//             } else {
//                 RadionButtonSelectedValueSet("roles", 5)
//                 // $('input[name ="roles"][value="2"]').attr('checked', true);
//             }
//         });
//         $('.myForm #exampleModal').modal();
//     });
//     // btnDelete
//     $('.btnDelete').on('click', function (event) {
//         event.preventDefault();
//         var href = $(this).attr("href");
//         $.get(href, function (userInfo, status) {
//             let userVar = userInfo[0];
//             let userRole = userInfo[1];
//             // th:readonly="true"
//             $('#dId').val(userVar.id);
//             $('#dName').val(userVar.firstName);
//             $('#dLastName').val(userVar.lastName);
//             $('#dAge').val(userVar.age);
//             $('#dEmail').val(userVar.email);
//             if (userRole === '[ADMIN]') {
//                 RadionButtonSelectedValueSet("roles", 1)
//             } else {
//                 RadionButtonSelectedValueSet("roles", 5)
//                 // $('input[name ="roles"][value="2"]').attr('checked', true);
//             }
//         });
//         $('.deleteForm #deleteModalModal').modal();
//     });
// });

// function RadionButtonSelectedValueSet(name, SelecedValue) {
//     $('input:radio[name="' + name + '"][value="' + SelecedValue + '"]').attr('checked', true);
// }

let dataList = [];


function listAllUsers() {
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", '/allusers');
    httpRequest.send();
    httpRequest.addEventListener('readystatechange', function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            dataList = JSON.parse(httpRequest.response);
            display();
        }
    })
}

function display() {
    var Box = " ";
    for (let i = 0; i < dataList.length; i++) {
        Box += `
             <tr> 
                <td>${dataList[i].id}</td>
                <td>${dataList[i].firstName}</td>
                <td>"${dataList[i].lastName}"</td>
                <td >${dataList[i].age}</td>
                <td>${dataList[i].email}</td>
                <td>${dataList[i].roleUser}</td>
                <td>
                 
                    <button  onclick='editUser("${dataList[i].id}", "${dataList[i].firstName}",  "${dataList[i].lastName}", " ${dataList[i].age}",  "${dataList[i].email}",  "${dataList[i].roleUser}" )' class="btn btn-light px-3 edit btnEdit" data-bs-toggle="modal" data-bs-target="#exampleModal" >Edit</button>
                    <button  onclick='showDeletedUser("${dataList[i].id}", "${dataList[i].firstName}",  "${dataList[i].lastName}", " ${dataList[i].age}",  "${dataList[i].email}",  "${dataList[i].roleUser}" )' class="btn btn-danger btnDelete"  data-bs-toggle="modal" data-bs-target="#deleteModal" >Delete</button>
                </td>
             </tr>
        `;

    }
    document.getElementById('dataList').innerHTML = Box;
}

function displayCurrentUser(dataList) {
    let cBox = "";
    cBox = `
             <tr > 
                <td>${dataList.id}</td>
                <td >${dataList.name}</td>
                <td >${dataList.lastName}</td>
                <td >${dataList.age}</td>
                <td >${dataList.email}</td>
                <td >${dataList.role}</td>
             </tr>
        `;
    document.getElementById('currentUserData').innerHTML = cBox;
}

function getCurrentUser() {
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", '/currentUser');
    httpRequest.send();
    httpRequest.addEventListener('readystatechange', function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            var cUser = JSON.parse(httpRequest.response);
            document.getElementById('userEmail').innerText = cUser.email;
            document.getElementById('userRole').innerText = cUser.role;
            displayCurrentUser(cUser)
        }
    })

}



function saveUser(){
    var  user = JSON.stringify(

            {
                firstName: document.querySelector('input[name="name"]').value,
                lastName: document.querySelector('input[name="lastName"]').value,
                age: document.querySelector('input[name="age"]').value,
                email: document.querySelector('input[name="email"]').value,
                password: document.querySelector('input[name="password"]').value,
                role: document.querySelector('input[name="roles"]:checked').value
            }
        );
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("POST", '/saveuser');
    // httpRequest.setRequestHeader(
    //     "Authorization", "Basic $2a$10$1gEChghJPV/t3sk/bqVTLuiQG4sPMz0IVxYV6SoC7HaFFzWBvrjYy");
    httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpRequest.send(user);
    listAllUsers();
}

function editUser(id, name, lastName, age, email, role) {
    // console.log("edit clicked");
    console.log("edit clicked  dd " );
    console.log("edit clicked  dd " +id);
    document.querySelector('#editId').value=id;
    document.querySelector('#editName').value=name;
    document.querySelector('#editLastName').value=lastName;
    document.querySelector('#editEmail').value=email;
    document.querySelector('#editAge').value=Number(age);
    if (role === 'ADMIN') {
                document.getElementById("ADMIN").checked=true;
            } else {
        document.getElementById("USER").checked=true;
                // $('input[name ="roles"][value="2"]').attr('checked', true);
            }
}

function saveEditedUser(){
    if(document.querySelector('input[name="editName"]').value.length>4 &&
        document.querySelector('input[name="editLastName"]').value.length>4 &&
        document.querySelector('input[name="editAge"]').value >2 &&
        document.querySelector('input[name="editPassword"]').value.length >0 &&
        document.querySelector('input[name="editRoles"]:checked').value >0){
        var  user = JSON.stringify(
            {
                id: document.querySelector('input[name="editId"]').value,
                firstName: document.querySelector('input[name="editName"]').value,
                lastName: document.querySelector('input[name="editLastName"]').value,
                age: document.querySelector('input[name="editAge"]').value,
                email: document.querySelector('input[name="editEmail"]').value,
                password: document.querySelector('input[name="editPassword"]').value,
                role: document.querySelector('input[name="editRoles"]:checked').value
            }
        );
        let httpRequest = new XMLHttpRequest();
        httpRequest.open("POST", '/saveuser');
        httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        httpRequest.send(user);
        listAllUsers();

    }
    else{
        alert("Sorry Model not saved, please enter correct data")
    }


}

function showDeletedUser(id, name, lastName, age, email, role) {
    document.querySelector('#dId').value=id;
    document.querySelector('#dName').value=name;
    document.querySelector('#dLastName').value=lastName;
    document.querySelector('#dEmail').value=email;
    document.querySelector('#dAge').value=Number(age);
    if (role === 'ADMIN') {
        document.getElementById("ADMIN").checked=true;
    } else {
        document.getElementById("USER").checked=true;
    }
}


function deleteUser(){
    var  user = JSON.stringify(
        {
            id: document.querySelector('input[name="dId"]').value,
        }
    );
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("POST", '/delete');
    httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpRequest.send(user);
    listAllUsers();
}






