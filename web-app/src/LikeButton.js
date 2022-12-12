import React, { Component } from "react";
import "./LikeButton.css"

class LikeButton extends React.Component {

    constructor(props) {
        super(props);
        this.handleLike = this.handleLike.bind(this);
    }

    handleLike() {
        this.props.likeSong(this.props.song.id);
    }

    render() {
        var imgName = "img/heart.png";
        if (this.props.likes.includes(this.props.song.id)) {
            imgName = "img/pink-heart.png";
        }
        return (
            <>
                <div className="like-button">   
                    <img src={imgName} onClick={(e) => { this.handleLike(); e.stopPropagation(); }}></img>
                </div>
            </>
        );
    }
}

export default LikeButton;