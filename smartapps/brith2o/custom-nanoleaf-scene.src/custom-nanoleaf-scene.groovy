/**
 *  Custom Nanoleaf Scene
 *
 *  Copyright 2018 Matt Pearson
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Custom Nanoleaf Scene",
    namespace: "brith2o",
    author: "Matt Pearson",
    description: "Customized code that sets a scene on Nanoleaf",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Select a Nanoleaf & Scene Name") {
    	// Require selection of a nanoleaf (requires the "Nanoleaf Aurora Smarter API" device handler)
		input "theNanoleaf", "device.nanoleafAuroraSmarterAPI", title: "Nanoleaf Device", required: "true"
        // A text string defining the scene name. Must be a valid scene name
        input "sceneName", "text", title: "Scene name", required: "true"
	}
    section("Activate when momentary button is pushed") {
		// Select the button (real or virtual) that sets the scene
        input "theButton", "capability.momentary"
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
    subscribe(theButton, "switch.on", appHandler)
}

def appHandler(evt) {
    log.debug "app event ${evt.name}:${evt.value} received. Pushing scene name ${settings.sceneName}"
    theNanoleaf.changeScene("${settings.sceneName}")
}
