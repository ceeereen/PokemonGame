package model;

public enum WeatherEnum {
    RAINY,HOT,SNOWY,STORM;

    //prints the weather condition
    public static String printWeatherCondition(WeatherEnum weatherEnum){
        if(weatherEnum==WeatherEnum.RAINY){
            return "Weather is rainy";
        } else if (weatherEnum==WeatherEnum.HOT) {
            return "Weather is hot";
        } else if (weatherEnum==WeatherEnum.SNOWY) {
            return "Snow has been started";
        }else{
            return "There is a storm that shatter the earth";
        }
    }

}
