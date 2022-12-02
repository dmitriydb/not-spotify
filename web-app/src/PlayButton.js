import React, { Component } from "react";
import "./PlayButton.css"

class PlayButton extends React.Component {


    render() {
        console.log("Playbutoon callback = " + this.props.changeSongCallBack);

        return (
            <>
                <div className="play-button" onClick={() => {this.props.changeSongCallBack(this.props.song)}}>   
                    <img src="img/play-button.png" ></img>
                </div>
            </>
        );
    }
}

export default PlayButton;