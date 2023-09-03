# Clever-Bank

Тестовое задание - разработка консольного приложения для Clever-Bank.

Основные сущности:
- Банк
- Счёт
- Пользователь
- Транзакция

Стек: Java 17, Gradle, PostgreSQL, JDBC, Lombok, ITextPDF, TestNG, Junit, Mockito, Log4j2
(Spring и Hibernate использовать запрещено)

Реализован полностью необходимый объем данных:
1. Банков ≥ 5
2. Пользователей ≥ 20
3. Счетов ≥ 40
4. Также у пользователей могут быть счета в разных банках
   Подготовлены ddl и dll sql-скрипты для заполнения базы данных.

   Полностью реализованы классы сущностей, интерфейсы м мх реализации в слоях сервиса и dao.
   Все методы на 100% покрыты тестами - в слое сервиса использованы юнит-тесты с мокированием,
   в слое dao - мнтеграционные тесты с взаимодействием с базой данных.
   Применено логирование с использованием Log4j2.
   Для работы в условиях многопоточности реализован пул соединений с базой данных (ConnectionPool).
   Реализована операция пополнения счета с выводом квитанции по заданному шаблону в консоль и в PDF-файл.
   Реализовано сохранение чеков в папке check после каждой операции.
   Применены шаблоны проектирования (Singleton, MVC и т.п.).
   При написании кода соблюдены хорошо известные принципы ООП, SOLID, DRY, YAGNI.
   Написанный код читаемый, поддерживаемый и содержит документацию в формате JavaDoc,
   соблюдается java code conventions.
   Проект размещен на github, с соблюдением git-flow: master - develop.
   Подготовлен данный README-файл с общим описанием проекта.
   Запуск и проверка функционирования проекта осуществляется при использовании тестовых методов,
   расположенных в папке src/test/java/com/art/clever и соответствующих тестируемым классам тестовых классах.
   Для классов сущностей реализованы и протестированы CRUD-операции,
   некоторые операции реализованы в нескольких вариантах.
   Например, для счета пользователя в банке (Account):
   CREATE - добавление аккаунта в базу данных: accountService.addAccount(Account account).
   возвращает true при успешном сохранении.
   READ - поиск всех аккаунтов: accountService.findAllAccounts(). возвращает List<Account> - список
   найденных аккаунтов;
   - поиск счета по его идентификатору - коду IBAN. применякмому в банковской системе:
   Optional<Account> findByCodeIBAN(String codeIBAN)
   - поиск всех аккаунтов конкретного пользователя: List<Account> findAllForUser(User user)
   - поиск всех аккаунтов из конкретного банка: List<Account> findAllForBank(Bank bank)
   UPDATE - обновляет данные счета по его ID: boolean updateAccount(Account account),
   возвращает true при успешном обновлении.
   DELETE - удаляет данные счета по его ID: boolean deleteAccount(String codeIBAN),
   возвращает true при успешном удалении.

   Покрытие тестами в проектк - максимальное для реализованной функциональности.
   (слои сервиса и dao - 100% покрытие)

   На текущий момент не реализована полностью вся функциональность в связи с ограниченным количеством свободного времени.
   Имеюшийся код подготовлен за выходные, с пятницы 01.09.23 по воскресенье 03.09.2023. При этом для завершения проекта
   требуется только свободное время, все необходимые технологии изучены и ранее применялись в других проектах.
   Спасибо за внимание.

   Артихович Александр.