$(function(){
    console.clear();
    console.log("--jq started.");

    //--------------------//
    $("#memo_top_dir").click(function(){
        console.log(">>> onclick event triggered.");

        $(".memo_sub_dir").toggle();

    }); //onclick for top_dir

    $("#file_top_dir").click(function(){
        console.log(">>> onclick event triggered.");

        $(".file_sub_dir").toggle();

    }); //onclick for top_dir

    $("#group_top_dir").click(function(){
        console.log(">>> onclick event triggered.");

        $(".group_sub_dir").toggle();

    }); //onclick for top_dir

    //========================================================//
//     /* When the user clicks on the button, 
// toggle between hiding and showing the dropdown content */

//     $("#profile").click(function(){
//         console.log(">>>> profile clicked");
//         $("dropdown-content").show(0);
//     }); //onclick for profile

//   // Close the dropdown if the user clicks outside of it
//   window.onclick = function(event) {
//     if (!event.target.matches('.dropbtn')) {
//       var dropdowns = document.getElementsByClassName("dropdown-content");
//       var i;
//       for (i = 0; i < dropdowns.length; i++) {
//         var openDropdown = dropdowns[i];
//         if (openDropdown.classList.contains('show')) {
//           openDropdown.classList.remove('show');
//         }
//       }
//     }
//   }
}) // .jq