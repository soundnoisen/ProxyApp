package com.proxyapp.core.ui.theme

import androidx.compose.ui.graphics.Color

object TagColors {
    val light = TagColorsScheme(
        protocolText = LightProtocolText,
        protocolBackground = LightProtocolBackground,
        speedLowText = LightSpeedLowText,
        speedLowBackground = LightSpeedLowBackground,
        speedMediumText = LightSpeedMediumText,
        speedMediumBackground = LightSpeedMediumBackground,
        speedHighText = LightSpeedHighText,
        speedHighBackground = LightSpeedHighBackground,
        countryText = LightCountryText,
        countryBackground = LightCountryBackground
    )
    val dark = TagColorsScheme(
        protocolText = DarkProtocolText,
        protocolBackground = DarkProtocolBackground,
        speedLowText = DarkSpeedLowText,
        speedLowBackground = DarkSpeedLowBackground,
        speedMediumText = DarkSpeedMediumText,
        speedMediumBackground = DarkSpeedMediumBackground,
        speedHighText = DarkSpeedHighText,
        speedHighBackground = DarkSpeedHighBackground,
        countryText = DarkCountryText,
        countryBackground = DarkCountryBackground
    )
}

data class TagColorsScheme(
    val protocolText: Color,
    val protocolBackground: Color,
    val speedLowText: Color,
    val speedLowBackground: Color,
    val speedMediumText: Color,
    val speedMediumBackground: Color,
    val speedHighText: Color,
    val speedHighBackground: Color,
    val countryText: Color,
    val countryBackground: Color
)