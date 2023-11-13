# Ktor and Supabase demo
This is a demo project to show how to use `Ktor` and `Supabase` together.
The main feature here is to demonstrate how to use supabase's Auth and Storage features.

Please read the [blog post](https://www.tomaszezula.com/keep-data-safe-in-multi-tenant-systems-a-case-for-supabase-and-row-level-security) for more details.

## Prerequisites
1. Sign up with supabase and create a [Supabase project](https://supabase.com/dashboard/projects) and get the API URL and API Key. Store them in the environment variables `SUPABASE_URL` and `SUPABASE_KEY` respectively.
2. Create a table called `todos` as follows:
```sql
-- 1. Create table
create table
    public.todos
(
    id      uuid primary key,
    user_id uuid references auth.users,
    task    text    not null,
    is_done boolean not null default false
) tablespace pg_default;
```
3. Restrict access to the table to only logged-in users. Each user can only see their own todos!:
```sql:
```sql
-- Enable RLS
alter table todos enable row level security;

-- Create policy
create policy "User can see their own todos only."
on todos
for select using ( auth.uid() = user_id );
```

## Running the project
```shell
./gradlew runFatJar
```
