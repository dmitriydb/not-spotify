import React, { Component } from "react";
import "./LikeButton.css"

class LikeButton extends React.Component {


    render() {
        return (
            <>
                <div className="like-button">   
                    <img src="img/heart.png"></img>
                </div>
            </>
        );
    }
}

export default LikeButton;