# avito-tech-trainee-task

Приложение прогноза погоды
Необходимо реализовать приложение, отображающее прогноз погоды.

### Требования к приложению:
1. Отображение прогноза погоды за текущий день
2. Отображение прогноза погоды за неделю
3. Возможность увидеть прогноз погоды в текущем городе
4. Возможность выбрать любой другой город, и узнать прогноз погоды в нем

#### API
В качестве API, для получения прогноза, можно использовать любой сервис; допускается использование бесплатных/ограниченных/пробных версий.\
Пример: [OpenWeatherMap API](https://openweathermap.org/api)

#### Дизайн
Решения о дизайне остаются полностью на ваше усмотрение.

#### Примечания
1. Задание нужно выполнять на Kotlin.
2. Выполненное задание нужно загрузить на github и отправить решение нам.

#### Решение 
В качестве API, для получения прогноза был выбран  [YandexWeatherApi](https://yandex.ru/dev/weather/)
Для запуска положите API_KEY в файл [ru/mozgolom112/weatherapp/utils/consts/RetrofitServiceConst.kt](https://github.com/mozgolom112/avito-tech-trainee-task/blob/main/app/src/main/java/ru/mozgolom112/weatherapp/utils/consts/RetrofitServiceConst.kt) 
в переменную APP_ID

#### Пример работы можете посмотреть ниже по ссылке
[Видео работы приложения](https://github.com/mozgolom112/avito-tech-trainee-task/blob/main/videoandscreenshots/work_example.mp4)

####Скриншоты

<table>
  <tr>
     <td align="center">Details Weather</td>
     <td align="center">7 Days Weather Forecast</td>
     <td align="center">Search City</td>

  </tr>
  <tr>
    <td valign="top"><img src="https://github.com/mozgolom112/avito-tech-trainee-task/blob/main/videoandscreenshots/daily_forecast.png"></td>
    <td valign="top"><img src="https://github.com/mozgolom112/avito-tech-trainee-task/blob/main/videoandscreenshots/weekly_forecast.png"></td>
    <td valign="top"><img src="https://github.com/mozgolom112/avito-tech-trainee-task/blob/main/videoandscreenshots/search_city.png"></td>
  </tr>

  <tr>
    <td align="center">SavedCities</td>
    <td align="center">Permissions</td>
<   td align="center">Error screen</td>
  </tr>

  <tr>
    <td valign="top"><img src="https://github.com/mozgolom112/avito-tech-trainee-task/blob/main/videoandscreenshots/saved_cities.png"></td>
    <td valign="top"><img src="https://github.com/mozgolom112/avito-tech-trainee-task/blob/main/videoandscreenshots/get_location_permission.png"></td>
    <td valign="top"><img src="https://github.com/mozgolom112/avito-tech-trainee-task/blob/main/videoandscreenshots/error_screen.png"></td>
  </tr>
 </table>
 <br>


#### Используемые библиотеки
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
* [Room](https://developer.android.com/topic/libraries/architecture/room)
* [Retrofit2](https://square.github.io/retrofit/)
* ...

