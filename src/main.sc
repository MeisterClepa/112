
theme: /

    state: newNode_0
        random:
            a: Тестер введи Имя и дату || tts = "Раз раз раз это войс тест", ttsEnabled = true
        image: https://bipbap.ru/wp-content/uploads/2017/08/16.jpg
        go!: /newNode_11
    @Transition
        {
          "boundsTo" : "/newNode_0",
          "then" : "/newNode_60"
        }
    state: newNode_1
        go!: /newNode_603
    @InputText
        {
          "boundsTo" : "",
          "actions" : [ ],
          "prompt" : "Имя",
          "varName" : "name",
          "then" : "/newNode_59"
        }
    state: newNode_60 || modal = true
        a: Имя1

        state:
            q: * *start *
            script:
              Zenflow.start();

        state: CatchText
            q: *
            script:
                addClientVarToSession("name", $parseTree.text);
            go!: /newNode_59

    state: newNode_59
        script:
            $reactions.timeout({interval: _.template('7 seconds', {variable: '$session'})($session), targetState: '/newNode_48'});
        go!: /newNode_61
    @InputPhoneNumber
        {
          "boundsTo" : "/newNode_59",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "prompt" : "Введите номер телефона",
          "varName" : "phone",
          "failureMessage" : [
            "Некорректный номер телефона"
          ],
          "then" : "/newNode_48"
        }
    state: newNode_61
        a: Введите номер телефона

        state: CatchPhoneNumber
            q: * $mobilePhoneNumber *
            script:
                addClientVarToSession("phone", $parseTree._mobilePhoneNumber);
            go!: /newNode_48

        state: WrongPhoneNumber
            script:
                var failureMessages = [
                    "Некорректный номер телефона"
                ];
                $temp.failureRandom = failureMessages[$reactions.random(failureMessages.length)];
                $reactions.answer($temp.failureRandom);
            go: ../CatchPhoneNumber

        state: CatchAll
            q: *
            go!: ../WrongPhoneNumber
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_61",
                name: "newNode_61 buttons",
                handler: function($context) {
                }
            });

    state: newNode_48
        random:
            a: Цифра текст
        go!: /newNode_50
    @InputNumber
        {
          "boundsTo" : "/newNode_48",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "prompt" : "Введите число",
          "varName" : "day",
          "failureMessage" : [
            "Введите число от 1 до 31",
            "число от 1 до 31",
            "в месяце 31 день"
          ],
          "then" : "/newNode_4",
          "minValue" : 1,
          "maxValue" : 31
        }
    state: newNode_50
        a: Введите число

        state: CatchNumber
            q: * $Number *
            script:
                var failureMessages = [
                    "Введите число от 1 до 31",
                    "число от 1 до 31",
                    "в месяце 31 день"
                ];
                var failureRandom = failureMessages[$reactions.random(failureMessages.length)];
                if ($parseTree._Number < 1) {
                    $reactions.answer(failureRandom);
                } else
                if ($parseTree._Number > 31) {
                    $reactions.answer(failureRandom);
                } else
                {
                    addClientVarToSession("day", $parseTree._Number);
                    $temp.day_ok = true;
                }
            if: $temp.day_ok
                go!: /newNode_4
            else:
                go: CatchNumber

        state: CatchAll
            q: *
            go!: ..
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_50",
                name: "newNode_50 buttons",
                handler: function($context) {
                }
            });

    state: newNode_4
        random:
            a: ну что поехали {{$session.name}} , сегодняшний день ({{$session.day}}) только начался || tts = "<speak> ты {{$session.name}} , сегодня {{$session.day}} , попробуем выжить</speak>", ttsEnabled = true
        go!: /newNode_5
    @Transition
        {
          "boundsTo" : "/newNode_4",
          "then" : "/newNode_6"
        }
    state: newNode_5
        go!: /newNode_6

    state: newNode_6
        random:
            a: Скажи или напиши, что хочешь
        go!: /newNode_7
    @IntentGroup
        {
          "boundsTo" : "/newNode_6",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [
                {
                  "name" : "Http"
                },
                {
                  "name" : "Обработка массивов",
                  "transition" : ""
                },
                {
                  "name" : "Завершим диалог",
                  "transition" : ""
                },
                {
                  "name" : "C чистого листа",
                  "transition" : ""
                },
                {
                  "name" : "Фразы",
                  "transition" : ""
                },
                {
                  "name" : "Вебхук",
                  "transition" : ""
                },
                {
                  "name" : "Файл",
                  "transition" : ""
                }
              ]
            }
          ],
          "global" : true,
          "fallback" : "/newNode_6",
          "intents" : [
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "Http"
                }
              ],
              "then" : "/newNode_8"
            },
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "Обработка массивов"
                }
              ],
              "then" : "/newNode_23"
            },
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "завершим диалог и очистим себя"
                }
              ],
              "then" : "/newNode_30"
            },
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "С чистого листа"
                }
              ],
              "then" : "/newNode_31"
            },
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "Фразы с паттерами"
                }
              ],
              "then" : "/newNode_32"
            },
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "Вебхук"
                }
              ],
              "then" : "/newNode_44"
            },
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "отправим файл"
                }
              ],
              "then" : "/newNode_53"
            }
          ]
        }
    state: newNode_7
        state: 1
            e!: Http

            go!: /newNode_8

        state: 2
            e!: Обработка массивов

            go!: /newNode_23

        state: 3
            e!: завершим диалог и очистим себя

            go!: /newNode_30

        state: 4
            e!: С чистого листа

            go!: /newNode_31

        state: 5
            e!: Фразы с паттерами

            go!: /newNode_32

        state: 6
            e!: Вебхук

            go!: /newNode_44

        state: 7
            e!: отправим файл

            go!: /newNode_53

        state: Fallback
            q: *
            go!: /newNode_6
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_7",
                name: "newNode_7 buttons",
                handler: function($context) {
                  $reactions.buttons([
                    {text: "Http"
                    },
                    {text: "Обработка массивов"
                    },
                    {text: "Завершим диалог"
                    },
                    {text: "C чистого листа"
                    },
                    {text: "Фразы"
                    },
                    {text: "Вебхук"
                    },
                    {text: "Файл"
                    },
                  ]);
                }
            });
    @HttpRequest
        {
          "boundsTo" : "",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "url" : "https://webhook.site/#!/f3d88a5d-bb26-4d13-8021-019b1e177006",
          "method" : "GET",
          "dataType" : "json",
          "body" : "",
          "okState" : "/newNode_14",
          "errorState" : "/newNode_22",
          "timeout" : 0,
          "headers" : [ ],
          "vars" : [
            {
              "name" : "answerget",
              "value" : "$httpResponse"
            }
          ]
        }
    state: newNode_8
        script:
            var headers = {
            };
            var result = $http.query("https://webhook.site/#!/f3d88a5d-bb26-4d13-8021-019b1e177006", {
                method: "GET",
                headers: headers,
                query: $session,
                dataType: "json",
                timeout: 0 || 10000
            });
            var $httpResponse = result.data;
            $session.httpStatus = result.status;
            $session.httpResponse = $httpResponse;
            if (result.isOk && result.status >= 200 && result.status < 300) {
                addClientVarToSession("answerget", $httpResponse);
                $reactions.transition("/newNode_14");
            } else {
                $reactions.transition("/newNode_22");
            }
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_8",
                name: "newNode_8 buttons",
                handler: function($context) {
                }
            });

    state: newNode_14
        random:
            a: Вернуло в get {{$session.answerget}}
        go!: /newNode_15
    @Transition
        {
          "boundsTo" : "/newNode_14",
          "then" : "/newNode_9"
        }
    state: newNode_15
        go!: /newNode_9
    @HttpRequest
        {
          "boundsTo" : "",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "url" : "https://webhook.site/2ac28c57-4e39-4c73-abec-b14132e4a7a3",
          "method" : "POST",
          "dataType" : "json",
          "body" : "{\n    \"clientId\": \"test\",\n    \"questionId\": \"2897f5c0-06bb-4c58-bd01-9886e58b794a\",\n    \"data\": {\n        \"answer\": \"Здравствуйте! Я знаю про количество проживающих в любом городе мира. Просто назовите город!\",\n        \"replies\": [\n            {\n                \"type\": \"text\",\n                \"text\": \"Здравствуйте! Я знаю про количество проживающих в любом городе мира. Просто назовите город!\",\n                \"state\": \"/newNode_0\"\n            }\n        ]\n    },\n    \"timestamp\": \"2018-09-28T13:59:25.008\"\n}",
          "okState" : "/newNode_16",
          "errorState" : "/newNode_22",
          "timeout" : 0,
          "headers" : [
            {
              "name" : "Content-Type",
              "value" : "text/xml"
            }
          ],
          "vars" : [
            {
              "name" : "answerpost",
              "value" : "$httpResponse"
            }
          ]
        }
    state: newNode_9
        script:
            var headers = {
                "Content-Type": _.template("text/xml", {variable: '$session'})($session)
            };
            var result = $http.query("https://webhook.site/2ac28c57-4e39-4c73-abec-b14132e4a7a3", {
                method: "POST",
                headers: headers,
                query: $session,
                body: _.template("{    \"clientId\": \"test\",    \"questionId\": \"2897f5c0-06bb-4c58-bd01-9886e58b794a\",    \"data\": {        \"answer\": \"Здравствуйте! Я знаю про количество проживающих в любом городе мира. Просто назовите город!\",        \"replies\": [            {                \"type\": \"text\",                \"text\": \"Здравствуйте! Я знаю про количество проживающих в любом городе мира. Просто назовите город!\",                \"state\": \"/newNode_0\"            }        ]    },    \"timestamp\": \"2018-09-28T13:59:25.008\"}", {variable: '$session'})($session),
                dataType: "json",
                timeout: 0 || 10000
            });
            var $httpResponse = result.data;
            $session.httpStatus = result.status;
            $session.httpResponse = $httpResponse;
            if (result.isOk && result.status >= 200 && result.status < 300) {
                addClientVarToSession("answerpost", $httpResponse);
                $reactions.transition("/newNode_16");
            } else {
                $reactions.transition("/newNode_22");
            }
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_9",
                name: "newNode_9 buttons",
                handler: function($context) {
                }
            });

    state: newNode_16
        random:
            a: вернуло в post {{$session.answerpost}}
        go!: /newNode_18
    @Transition
        {
          "boundsTo" : "/newNode_16",
          "then" : "/newNode_10"
        }
    state: newNode_18
        go!: /newNode_10
    @HttpRequest
        {
          "boundsTo" : "",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "url" : "https://webhook.site/2ac28c57-4e39-4c73-abec-b14132e4a7a3",
          "method" : "PUT",
          "dataType" : "json",
          "body" : "{Put}",
          "okState" : "/newNode_17",
          "errorState" : "/newNode_22",
          "timeout" : 0,
          "headers" : [
            {
              "name" : "",
              "value" : ""
            }
          ],
          "vars" : [
            {
              "name" : "answerput",
              "value" : "$httpResponse"
            }
          ]
        }
    state: newNode_10
        script:
            var headers = {
                "": _.template("", {variable: '$session'})($session)
            };
            var result = $http.query("https://webhook.site/2ac28c57-4e39-4c73-abec-b14132e4a7a3", {
                method: "PUT",
                headers: headers,
                query: $session,
                body: _.template("{Put}", {variable: '$session'})($session),
                dataType: "json",
                timeout: 0 || 10000
            });
            var $httpResponse = result.data;
            $session.httpStatus = result.status;
            $session.httpResponse = $httpResponse;
            if (result.isOk && result.status >= 200 && result.status < 300) {
                addClientVarToSession("answerput", $httpResponse);
                $reactions.transition("/newNode_17");
            } else {
                $reactions.transition("/newNode_22");
            }
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_10",
                name: "newNode_10 buttons",
                handler: function($context) {
                }
            });

    state: newNode_17
        random:
            a: вернуло в put {{$session.answerput}}
        go!: /newNode_19
    @Transition
        {
          "boundsTo" : "/newNode_17",
          "then" : "/newNode_11"
        }
    state: newNode_19
        go!: /newNode_11
    @HttpRequest
        {
          "boundsTo" : "",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "url" : "https://webhook.site/2ac28c57-4e39-4c73-abec-b14132e4a7a3",
          "method" : "DELETE",
          "dataType" : "json",
          "body" : "{delete}",
          "okState" : "/newNode_20",
          "errorState" : "/newNode_22",
          "timeout" : 0,
          "headers" : [ ],
          "vars" : [
            {
              "name" : "answerdelete",
              "value" : "$httpResponse"
            }
          ]
        }
    state: newNode_11
        script:
            var headers = {
            };
            var result = $http.query("https://webhook.site/2ac28c57-4e39-4c73-abec-b14132e4a7a3", {
                method: "DELETE",
                headers: headers,
                query: $session,
                body: _.template("{delete}", {variable: '$session'})($session),
                dataType: "json",
                timeout: 0 || 10000
            });
            var $httpResponse = result.data;
            $session.httpStatus = result.status;
            $session.httpResponse = $httpResponse;
            if (result.isOk && result.status >= 200 && result.status < 300) {
                addClientVarToSession("answerdelete", $httpResponse);
                $reactions.transition("/newNode_20");
            } else {
                $reactions.transition("/newNode_22");
            }
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_11",
                name: "newNode_11 buttons",
                handler: function($context) {
                }
            });

    state: newNode_20
        random:
            a: вернуло в delete {{$session.answerdelete}}
        go!: /newNode_21
    @Transition
        {
          "boundsTo" : "/newNode_20",
          "then" : "/newNode_12"
        }
    state: newNode_21
        go!: /newNode_12

    state: newNode_12
        random:
            a: Проверка показала что запросы работает
        go!: /newNode_13
    @Transition
        {
          "boundsTo" : "/newNode_12",
          "then" : "/newNode_6"
        }
    state: newNode_13
        go!: /newNode_6

    state: newNode_22
        random:
            a: ой йой ошибка в http блоке
    @HttpRequest
        {
          "boundsTo" : "",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "url" : "http://tools.aimylogic.com/api/rss2json?url=https://yandex.ru/blog/company/rss",
          "method" : "GET",
          "dataType" : "json",
          "body" : "",
          "okState" : "/newNode_24",
          "errorState" : "/newNode_22",
          "timeout" : 0,
          "headers" : [ ],
          "vars" : [
            {
              "name" : "items",
              "value" : "$httpResponse"
            }
          ]
        }
    state: newNode_23
        script:
            var headers = {
            };
            var result = $http.query("http://tools.aimylogic.com/api/rss2json?url=https://yandex.ru/blog/company/rss", {
                method: "GET",
                headers: headers,
                query: $session,
                dataType: "json",
                timeout: 0 || 10000
            });
            var $httpResponse = result.data;
            $session.httpStatus = result.status;
            $session.httpResponse = $httpResponse;
            if (result.isOk && result.status >= 200 && result.status < 300) {
                addClientVarToSession("items", $httpResponse);
                $reactions.transition("/newNode_24");
            } else {
                $reactions.transition("/newNode_22");
            }
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_23",
                name: "newNode_23 buttons",
                handler: function($context) {
                }
            });

    state: newNode_24
        if: $session.items.next()
            go!: /newNode_27
        else:
            go!: /newNode_28

    state: newNode_25
        if: $session.items.prev()
            go!: /newNode_27
        else:
            go!: /newNode_28

    state: newNode_26
        if: $session.items.random()
            go!: /newNode_27
        else:
            go!: /newNode_28

    state: newNode_27
        random:
            a: {{$session.items.current().title}}
        buttons:
            "дальше" -> /newNode_24
            "назад" -> /newNode_25
            "рандом" -> /newNode_26
            {text: "Подробнее", url: "{{$session.items.current().link}}"}
            {text: "2 кнопка подробнее", url: "{{$session.items.current().link}}"}

    state: newNode_28
        random:
            a: Кончились новости
        go!: /newNode_29
    @Transition
        {
          "boundsTo" : "/newNode_28",
          "then" : "/newNode_23"
        }
    state: newNode_29
        go!: /newNode_23
    @EndSession
        {
          "boundsTo" : ""
        }
    state: newNode_30
        script:
            $session = new Object();
            $response.endSession = true;

    state: newNode_31
        random:
            a: Больше нет имени {{$session.name}}

    state: newNode_32
        random:
            a: введите паттерн по смыслу
        go!: /newNode_33
    @IntentGroup
        {
          "boundsTo" : "/newNode_32",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "global" : false,
          "fallback" : "/newNode_6",
          "intents" : [
            {
              "phrases" : [
                {
                  "type" : "pattern",
                  "value" : "Скидку хочу на $TEXT"
                }
              ],
              "then" : "/newNode_37"
            },
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "Я в городе $CITY"
                },
                {
                  "type" : "example",
                  "value" : "Мой город $CITY"
                }
              ],
              "then" : "/newNode_41"
            },
            {
              "phrases" : [
                {
                  "type" : "example",
                  "value" : "Давление у меня $NUMBER::SYS на $NUMBER::DIA"
                }
              ],
              "then" : "/newNode_34"
            }
          ]
        }
    state: newNode_33
        state: 1
            q: Скидку хочу на $TEXT

            go!: /newNode_37

        state: 2
            e: Я в городе $CITY
            e: Мой город $CITY

            go!: /newNode_41

        state: 3
            e: Давление у меня $NUMBER::SYS на $NUMBER::DIA

            go!: /newNode_34

        state: Fallback
            q: *
            go!: /newNode_6
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_33",
                name: "newNode_33 buttons",
                handler: function($context) {
                }
            });

    state: newNode_34
        if: (30 < $session.DIA) & ($session.DIA<$session.SYS) & ($session.SYS < 300)
            go!: /newNode_36
        else:
            go!: /newNode_35

    state: newNode_35
        random:
            a: ты труп [{{$session.SYS}} на {{$session.DIA}}] {{$session.name}}
        go!: /newNode_39
    @Transition
        {
          "boundsTo" : "/newNode_35",
          "then" : "/newNode_32"
        }
    state: newNode_39
        go!: /newNode_32

    state: newNode_36
        random:
            a: Везет тебе ({{$session.SYS}} на {{$session.DIA}}) {{$session.name}}
        go!: /newNode_40
    @Transition
        {
          "boundsTo" : "/newNode_36",
          "then" : "/newNode_32"
        }
    state: newNode_40
        go!: /newNode_32

    state: newNode_37
        random:
            a: Много хочешь {{$session.name}} на {{$session.TEXT}} возьми кредит
        go!: /newNode_38
    @Transition
        {
          "boundsTo" : "/newNode_37",
          "then" : "/newNode_32"
        }
    state: newNode_38
        go!: /newNode_32

    state: newNode_41
        random:
            a: Ты гражданин {{$session.name}} с города {{$session.CITY}}
        go!: /newNode_42
    @Transition
        {
          "boundsTo" : "/newNode_41",
          "then" : "/newNode_6"
        }
    state: newNode_42
        go!: /newNode_6
    @InputText
        {
          "boundsTo" : "",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "prompt" : "Переменная",
          "varName" : "webhook",
          "then" : "/newNode_45"
        }
    state: newNode_44 || modal = true
        a: Переменная

        state:
            q: * *start *
            script:
              Zenflow.start();

        state: CatchText
            q: *
            script:
                addClientVarToSession("webhook", $parseTree.text);
            go!: /newNode_45
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_44",
                name: "newNode_44 buttons",
                handler: function($context) {
                }
            });

    state: newNode_45
        random:
            a: {{$session.webhook}} вот
        buttons:
            "получить хуки" -> /newNode_46

    state: newNode_46
        random:
            a: Ты есть {{$session.name}}
    @InputFile
        {
          "boundsTo" : "",
          "actions" : [
            {
              "type" : "buttons",
              "buttons" : [ ]
            }
          ],
          "prompt" : "Прикрепите файл",
          "varName" : "userFile",
          "failureMessage" : [ ],
          "then" : "/newNode_54",
          "errorState" : "/newNode_55"
        }
    state: newNode_53 || modal = true
        a: Прикрепите файл

        state: fileEvent
            event: fileEvent
            script:
                if ($request.data && $request.data.eventData && $request.data.eventData.length > 0) {
                    addClientVarToSession("userFile", $request.data.eventData[0].url);
                    addClientVarToSession("userFiles", $request.data.eventData);
                    $reactions.transition("/newNode_54");
                } else {
                    $reactions.transition("/newNode_55");
                }

        state: CatchText
            q: *
            script:
                $reactions.transition("/newNode_55");
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_53",
                name: "newNode_53 buttons",
                handler: function($context) {
                }
            });

    state: newNode_54
        random:
            a: Вот твой файл {{$session.userFile}}
        go!: /newNode_56
    @Transition
        {
          "boundsTo" : "/newNode_54",
          "then" : "/newNode_57"
        }
    state: newNode_56
        go!: /newNode_57

    state: newNode_55
        random:
            a: Нет файла
        go!: /newNode_58
    @Transition
        {
          "boundsTo" : "/newNode_55",
          "then" : "/newNode_57"
        }
    state: newNode_58
        go!: /newNode_57
    @EndSession
        {
          "boundsTo" : ""
        }
    state: newNode_57
        script:
            $session = new Object();
            $response.endSession = true;
