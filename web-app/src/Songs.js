import React, { Component } from "react";
import "./Songs.css"
import SongItem
 from "./SongItem";
class Songs extends React.Component {

    render() {
        console.log("Songs = " + JSON.stringify(this.props.songs));

        var content = []
        for (var i = 0; i < 10; i++) {
            var song = this.props.songs[i];
            content.push(<SongItem key={i} song={song} changeSongCallBack={this.props.changeSongCallBack}/>)
        }

        console.log(`Now we are having ${this.props.songs.length} songs`);

        return (
            <>
                <div className="songs">
                    {content}
                </div>
            </>
        );
    }
}

export default Songs;