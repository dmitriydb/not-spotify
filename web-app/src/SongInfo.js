import React, { Component } from "react";
import "./SongInfo.css"

class SongInfo extends React.Component {
   


    render() {
        var titleStyle = "song-title";
        var bandStyle = "band-title";
        if (this.props.active) {
             titleStyle = "song-title-active";
             bandStyle = "band-title-active";
        }
        return (
            <>
                <div className="song-info">   
                    <p className={titleStyle}>{this.props.title}</p>
                    <p className={bandStyle}>{this.props.band}</p>
                </div>
            </>
        );
    }
}

export default SongInfo;