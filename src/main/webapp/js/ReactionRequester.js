document.addEventListener("DOMContentLoaded", function () {
    let videoUuid = document.getElementsByTagName('video')[0].id;
    let reactionTypeButton = document.getElementsByClassName('reactionType')

    for (let i = 0; i < reactionTypeButton.length; i++) {
        reactionTypeButton[i].addEventListener("click", function (ev) {
            let reactionTypeButton = document.getElementsByClassName('reactionType');
            let id = reactionTypeButton[i].getAttribute("id");
            let reactionType = 0;

            if (id === "like") {
                reactionType = 1;
            } else if (id === "dislike") {
                reactionType = -1;
            }
            reactionTypeButton[i].removeAttribute("checked");
            ev.stopPropagation();
            return updateReactions(reactionType, videoUuid);
        })
    }


    function updateReactions(reaction, videoUuid) {

        return fetch('/MyTube/reaction?' + 'videoUuid=' + videoUuid + "&reaction=" + reaction, {
            method: "POST"
        }).then(res => {
            return res.json();
        }).then(json => {
            if (json.redirect === true) {
                window.location.replace(json.location);
            } else {
                updateInf(json.likes, json.dislikes, json.reaction);
            }
        })
    }

    function updateInf(likes, dislikes, reaction) {
        let likeLbl = document.getElementById("likeLabel");
        let dislikeLbl = document.getElementById("dislikeLabel");
        let voidLbl = document.getElementById("voidLabel");
        likeLbl.innerText = "like " + likes;
        dislikeLbl.innerText = "dislike " + dislikes;
        if (reaction === -1) {
            dislikeLbl.setAttribute("checked", "");
        } else if (reaction === 0) {
            voidLbl.setAttribute("checked", "");
        } else if (reaction === 1) {
            likeLbl.setAttribute("checked", "");
        }
    }
})
