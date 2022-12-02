import React, { Component } from "react";
import "./SongInfoButton.css"

class SongInfoButton extends React.Component {


    render() {
        return (
            <>
                <div className="song-info-button">   
                    <img src="img/song-info.png"></img>
                </div>
            </>
        );
    }
}

export default SongInfoButton;