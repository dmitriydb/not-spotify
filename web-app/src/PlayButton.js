import React, { Component } from "react";
import "./PlayButton.css"

class PlayButton extends React.Component {


    render() {
        var icon = "img/song-playing.png";

        if (this.props.song == this.props.currentSong) {
            icon = "img/play-button.png";
        }
        return (
            <>
                <div className="play-button">   
                    <img src={icon} ></img>
                </div>
            </>
        );
    }
}

export default PlayButton;