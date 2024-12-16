theme: /

    state: Start 
        q!: start
        a: Вы сказали и бот ответил: {{$parseTree.text}}

    state: Stop
        q!: stop
        a: Вы прервали меня!
        
    state: buttons
        q!: Кнопки
        a: кнопки
        buttons:
            "Первая" -> /Start
            "Вторая" -> /Stop
            "Третья" -> /CatchAll
    
    state:
        q!: Изображение
        image: https://just-ai.com/wp-content/uploads/2020/02/logo_og-2x-1.png
        
    state:
        q!: Аудио
        audio: https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3
        
    state: 
        q!: Таймаут
        a: 1...
        script: $reactions.timeout({interval: '5 seconds', targetState: '/afterTimeout'});
        
    state: afterTimeout
        a: 2
        
    state: inlineButtons
        q!: Конпка-ссылка
        a: inlineButtons
        inlineButtons:
            {text:"inlineButton1", url:"https://www.google.ru/"}  
      
    state: file
        event: fileEvent
        a: Файл получен
      

    state: CatchAll
        q!: *
        a: Скажите боту что-то осмысленное
                

    state: LivechatReset
        event!: livechatFinished
        go!: /CatchAll
        
    state: Operator
        q!: Оператор
        if: !hasOperatorsOnline()
            go!: Switch/NoOperatorsOnline
        else:
            a: Переходим?
            buttons:
                "Да" -> Switch
                "Нет" -> /CatchAll

        state: Switch
            a: Переводим на оператора...
            buttons:
                {"text":"Закрыть диалог","storeForViberLivechat":true}
            script:
                $response.replies = $response.replies || [];
                $response.replies
                 .push({
                    type:"switch",
                    appendCloseChatButton: true,
                    closeChatPhrases: ["Закрыть диалог", "/closeLiveChat"],
                    firstMessage: $client.history,
                    lastMessage: "Клиент закрыл диалог"
                });

            state: NoOperatorsOnline
                a: Операторов сейчас нет на месте
                buttons:
                    "Вернуться к боту" -> /Start

                state: GetUserInfo
                    q: *
                    script:
                        $response.replies = $response.replies || [];
                        $response.replies
                         .push({
                            type:"switch",
                            firstMessage: $parseTree.text + '\nДанное сообщение было отправлено в нерабочее время.',
                            ignoreOffline: true,
                            oneTimeMessage: true
                         });
                    go!: /CatchAll
        
    state: reset
        q!: Новая сессия
        script:
            $reactions.newSession({message: "/start", data: $request.data});    